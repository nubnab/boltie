package com.boltie.backend.services;

import com.boltie.backend.dto.LoginDto;
import com.boltie.backend.dto.RegisterDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.dto.UserRecordingsDto;
import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.User;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.UserMapper;
import com.boltie.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StreamService streamService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       StreamService streamService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.streamService = streamService;
    }

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

        User user = userMapper.registerToUser(registerDto);

        String usernameToLower = user.getUsername().toLowerCase();

        user.setUsername(usernameToLower);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.password())));

        user.setStream(streamService.generateDefaultStream(user));

        List<Recording> recordings = new ArrayList<>();

        user.setRecordings(recordings);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
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
}
