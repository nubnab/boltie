package com.boltie.backend.facades;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.*;
import com.boltie.backend.entities.User;
import com.boltie.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserFacadeService {
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;


    public Authentication authenticateUser(String token) {
        DecodedJWT jwt = userAuthProvider.verifyToken(token);

        UserDto user = userService.findByUsername(jwt.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null,
                Collections.singletonList(user.getRole()));
    }

    public UserDto validateRefreshToken(String refreshToken) {
        DecodedJWT jwt = userAuthProvider.verifyToken(refreshToken);

        return userService.findByUsername(jwt.getIssuer());
    }

    public String createToken(UserDto user) {
        return userAuthProvider.createToken(user);
    }

    public String createRefreshToken(UserDto user) {
        return userAuthProvider.createRefreshToken(user);
    }

    public UserDto login(LoginDto loginDto) {
        return userService.login(loginDto);
    }

    public UserDto registerUser(RegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }

    public void changeUsername(UsernameChangeDto usernameChangeDto, HttpServletRequest request) {
        String username = userAuthProvider.getUsernameFromRequest(request);
        LoginDto authenticateUser = new LoginDto(username, usernameChangeDto.password());
        UserDto userIsAuthenticated = userService.login(authenticateUser);

        if(userIsAuthenticated != null) {
            User user = userService.getUserByUsername(username);
            user.setUsername(usernameChangeDto.newUsername());
            userService.saveUser(user);
        }
    }

}
