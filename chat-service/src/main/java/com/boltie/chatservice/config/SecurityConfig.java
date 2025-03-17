package com.boltie.chatservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UnauthorizedAccessHandler unauthorizedAccessHandler;
    private final UserAuthProvider userAuthProvider;

    @Value("${chat.api.key}")
    private String CHAT_API_KEY;

    @Bean
    @Order(1)
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .securityMatcher("/ws")
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll())
                .exceptionHandling(e -> e
                        .accessDeniedHandler(unauthorizedAccessHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .securityMatcher("/create-chat/**", "/health")
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .accessDeniedHandler(unauthorizedAccessHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ApiKeyFilter(userAuthProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

   @Bean
   @Order(3)
   public SecurityFilterChain publicFilterChain (HttpSecurity http) throws Exception {
       return http
               .csrf(AbstractHttpConfigurer::disable)
               .cors(Customizer.withDefaults())
               .securityMatcher("/chat/**")
               .authorizeHttpRequests(requests -> requests
                       .anyRequest().permitAll())
               .sessionManagement(session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .build();
   }

}
