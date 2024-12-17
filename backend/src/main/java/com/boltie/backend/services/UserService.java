package com.boltie.backend.services;

import com.boltie.backend.dto.LoginDto;
import com.boltie.backend.dto.RegisterDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.entities.User;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.mappers.UserMapper;
import com.boltie.backend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.login())
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

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.password())));

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





}
