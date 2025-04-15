package com.boltie.backend.unit.validators;

import com.boltie.backend.annotations.NoSpecialSymbols;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecialSymbolValidatorTest {

    private static Validator validator;

    private record TestUser (
            @NoSpecialSymbols
            String username) {
    }

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"username", "user123", "123user", "a1b2c3", "1231user ", " user"})
    void isValid_shouldReturnTrueForValidUsernames(String validUsername) {
        Set<ConstraintViolation<TestUser>> violations = validator.validate(new TestUser(validUsername));
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"user@name", "us-er123", "a1b2;c3", "user!", "USER", "user name"})
    void isValid_shouldReturnFalseForInvalidUsernames(String invalidUsername) {
        Set<ConstraintViolation<TestUser>> violations = validator.validate(new TestUser(invalidUsername));
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Username can only contain lowercase letters and numbers (a-z, 0-9)");
    }
}
