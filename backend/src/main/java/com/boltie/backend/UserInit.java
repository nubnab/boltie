package com.boltie.backend;

import com.boltie.backend.entities.Stream;
import com.boltie.backend.entities.User;
import com.boltie.backend.enums.Role;
import com.boltie.backend.repositories.UserRepository;
import com.boltie.backend.services.MessageQueueService;
import com.boltie.backend.services.StreamService;
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
    private final StreamService streamService;
    private final MessageQueueService messageQueueService;


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

            admin.setStream(streamService.generateDefaultStream(admin));

            messageQueueService.publishChatCreationRequest(admin.getId());

            userRepository.save(admin);
        }
    }
}
