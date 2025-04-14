package com.boltie.backend.validators;

import com.boltie.backend.annotations.NoSpecialSymbols;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SpecialSymbolValidator implements ConstraintValidator<NoSpecialSymbols, String> {

    private final Pattern VALID_USERNAME = Pattern.compile("^[a-z0-9]+$");

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) { return true; }

        if (!VALID_USERNAME.matcher(username.trim()).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Username can only contain lowercase letters and numbers (a-z, 0-9)"
            ).addConstraintViolation();
            return false;
        }
        return true;
    }
}
