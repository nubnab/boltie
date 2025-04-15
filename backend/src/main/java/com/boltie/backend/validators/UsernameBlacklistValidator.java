package com.boltie.backend.validators;

import com.boltie.backend.annotations.NotBlacklisted;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class UsernameBlacklistValidator implements ConstraintValidator<NotBlacklisted, String> {
    private List<String> blacklist;

    @Override
    public void initialize(NotBlacklisted constraintAnnotation) {
        try (InputStream input = new ClassPathResource("config/username-blacklist.txt").getInputStream()) {
            blacklist = new BufferedReader(new InputStreamReader(input))
                    .lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load username blacklist", e);
        }
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !blacklist.contains(username.toLowerCase().trim());
    }
}
