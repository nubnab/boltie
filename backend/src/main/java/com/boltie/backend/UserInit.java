package com.boltie.backend;

import com.boltie.backend.entities.User;
import com.boltie.backend.enums.Role;
import com.boltie.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Order(1)
public class UserInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        if(userRepository.count() == 0) {
            User admin = User
                    .builder()
                    .id(1L)
                    .username("admin")
                    .password(passwordEncoder.encode(CharBuffer.wrap("ChangeMe_64@".toCharArray())))
                    .role(Role.ROLE_ADMIN)
                    .recordings(new ArrayList<>())
                    .watchHistory(new ArrayList<>())
                    .watchLaterList(new ArrayList<>())
                    .build();

            userRepository.save(admin);
        }
    }
}
