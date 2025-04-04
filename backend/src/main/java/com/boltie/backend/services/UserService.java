package com.boltie.backend.services;

import com.boltie.backend.dto.LoginDto;
import com.boltie.backend.dto.RegisterDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.dto.UserRecordingsDto;
import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.User;
import com.boltie.backend.enums.Role;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.UserMapper;
import com.boltie.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StreamService streamService;
    private final MessageQueueService messageQueueService;


    public UserDto login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.username().toLowerCase())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(loginDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Wrong password", HttpStatus.BAD_REQUEST);
    }

    public UserDto registerUser(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByUsername(registerDto.username());
        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }

        User user = User
                .builder()
                .username(registerDto.username().toLowerCase())
                .password(passwordEncoder.encode(CharBuffer.wrap(registerDto.password())))
                .role(Role.ROLE_USER)
                .recordings(new ArrayList<>())
                .watchHistory(new ArrayList<>())
                .build();

        user.setStream(streamService.generateDefaultStream(user));

        User savedUser = userRepository.save(user);

        messageQueueService.publishChatCreationRequest(savedUser.getId());

        return userMapper.toUserDto(user);
    }

    public UserDto findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return userMapper.toUserDto(optionalUser.get());
        }
        throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
    }

    public UserRecordingsDto fetchUserRecordings(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return userMapper.toUserRecordingsDto(optionalUser.get());
        }
        throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
    }

    public void addUserRecordingsToDb(String username, List<String> recordingTitles) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String streamTitle = user.getStream().getTitle();

            for (String recordingTitle : recordingTitles) {
                Recording recording = new Recording();
                recording.setTitle(streamTitle);
                recording.setFolderName(recordingTitle);
                recording.setUser(user);

                user.getRecordings().add(recording);
                userRepository.save(user);
            }
        }
    }

    public void changeStreamAndRecordingTitle(String newTitle, String username) {
        User user = getUserByUsername(username);
        user.getStream().setTitle(newTitle);
        user.getRecordings().getLast().setTitle(newTitle);

        userRepository.save(user);
    }

    public void changeStreamTitle(String newTitle, String username) {
        User user = getUserByUsername(username);
        user.getStream().setTitle(newTitle);

        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
    }

}
