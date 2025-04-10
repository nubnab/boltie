package com.boltie.backend.services;

import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.*;
import com.boltie.backend.entities.Category;
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
import java.util.stream.Collectors;

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
                .watchLaterList(new ArrayList<>())
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
            Category category = user.getStream().getCategory();

            for (String recordingTitle : recordingTitles) {
                Recording recording = Recording.builder()
                        .title(streamTitle)
                        .folderName(recordingTitle)
                        .user(user)
                        .category(category)
                        .build();

                user.getRecordings().add(recording);
                userRepository.save(user);
            }
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
    }

    public List<UserRoleDto> fetchUserRoles() {
        List<UserRoleDto> userRoles = new ArrayList<>();

        userRepository.findAll().forEach(user -> {
            userRoles.add(userMapper.toUserRoleDto(user));
        });

        return userRoles;
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new AppException("Unknown user", HttpStatus.NOT_FOUND);
    }

    public void changeUserRole(Long id, Role role) {
        User user = getUserById(id);
        user.setRole(role);
        userRepository.save(user);
    }

    public List<UsernameDto> getAllUsernames() {
        return userRepository.findAll()
                .stream().map(userMapper::toUsernameDto).collect(Collectors.toList());
    }

    public void saveUser(User user) {
        user.setStream(streamService.generateDefaultStream(user));
        userRepository.save(user);
    }

}
