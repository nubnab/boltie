package com.boltie.backend.facades;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.LoginDto;
import com.boltie.backend.dto.RegisterDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.services.StreamService;
import com.boltie.backend.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserFacadeService {
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;

    public UserFacadeService(UserAuthProvider userAuthProvider,
                             UserService userService) {
        this.userAuthProvider = userAuthProvider;
        this.userService = userService;
    }

    public Authentication authenticateUser(String token) {
        DecodedJWT jwt = userAuthProvider.verifyToken(token);

        UserDto user = userService.findByUsername(jwt.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
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




}
