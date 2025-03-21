package com.boltie.chatservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.boltie.chatservice.dto.CustomUserDetails;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;

@Component
@Getter
public class AuthProvider {

    @Value("${security.jwt.token.key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Authentication authenticateUser(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        CustomUserDetails customUserDetails = createCustomUserDetails(decodedJWT);

        return new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
    }

    private CustomUserDetails createCustomUserDetails (DecodedJWT decodedJWT) {
        Long userId = decodedJWT.getClaim("userId").asLong();
        String username = decodedJWT.getIssuer();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString());

        return new CustomUserDetails(userId, username, Collections.singletonList(role));
    }

    private DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }
}