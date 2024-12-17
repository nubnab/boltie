package com.boltie.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank(message = "Username cannot be empty!")
        @Size(min = 4, max = 255)
        String login,

        @NotEmpty(message = "Password cannot be empty!")
        @Size(min = 8, max = 255)
        char[] password) {
}
