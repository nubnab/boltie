package com.boltie.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.boltie.backend.dto.UserDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        //TODO: Replace Date with java.time impl
        final Date issuedAt = new Date();
        final Date expiresAt = new Date(issuedAt.getTime() + 1000L * 60 * 15); //15 minutes

        return createJWT(userDto, issuedAt, expiresAt);
    }

    public String createRefreshToken(UserDto userDto) {
        //TODO: Replace Date with java.time impl
        final Date issuedAt = new Date();
        final Date expiresAt = new Date(issuedAt.getTime() + 1000L * 60 * 60 * 24 * 30); //30 days

        return createJWT(userDto, issuedAt, expiresAt);
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);

            DecodedJWT jwt = verifyToken(token);

            return jwt.getIssuer();
        }
        return null;
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    private String createJWT(UserDto userDto, Date issuedAt, Date expiresAt) {
        return JWT.create()
                .withIssuer(userDto.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withClaim("userId", userDto.getId())
                .withClaim("role", userDto.getRole().name())
                .sign(Algorithm.HMAC256(secretKey));
    }
}
