package com.boltie.backend.integration.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIT {

    @Autowired
    private MockMvc mockMvc;

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Test
    void securityFilterChain_ShouldAcceptAllowedOrigins() throws Exception {
        mockMvc.perform(options("/api")
                .header("Origin", allowedOrigins)
                .header("Access-Control-Request-Method", "GET"))
                .andExpectAll(
                        header().exists("Access-Control-Allow-Origin"),
                        header().exists("Access-Control-Allow-Methods"),
                        header().exists("Access-Control-Allow-Credentials")
                );
    }

    @Test
    void securityFilterChain_ShouldRejectDisallowedOrigins() throws Exception {
        mockMvc.perform(get("/api")
                .header("Origin", "https://evil-site.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    void securityFilterChain_ShouldRequireCsrfToken() throws Exception {
        mockMvc.perform(post("/api")
                .with(csrf()))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api"))
                .andExpect(status().isForbidden());
    }

    @Test
    void securityFilterChain_ShouldSetCsrfCookie() throws Exception {
        mockMvc.perform(get("/api"))
                .andExpect(cookie().exists("XSRF-TOKEN"));
    }

}
