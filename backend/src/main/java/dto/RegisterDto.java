package dto;

import com.boltie.backend.annotations.NoSpecialSymbols;
import com.boltie.backend.annotations.NotBlacklisted;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterDto(

        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 25, message = "Username must be between 4 and 25 characters")
        @NotBlacklisted
        @NoSpecialSymbols
        String username,

        @NotEmpty(message = "Password is required")
        @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
        char[] password) {

}
