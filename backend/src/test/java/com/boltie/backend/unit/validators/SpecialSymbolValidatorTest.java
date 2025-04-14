package com.boltie.backend.unit.validators;

import com.boltie.backend.validators.SpecialSymbolValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialSymbolValidatorTest {

    private SpecialSymbolValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @BeforeEach
    void setUp() {
        validator = new SpecialSymbolValidator();
    }

    @Test
    void isValid_ShouldReturnTrueForNullInput() {
        assertThat(validator.isValid(null, context)).isTrue();
        verifyNoInteractions(context);
    }

    @ParameterizedTest
    @ValueSource(strings = {"username", "user123", "123user", "a1b2c3", "1231user ", " user"})
    void isValid_shouldReturnTrueForValidUsernames(String validUsername) {
        assertThat(validator.isValid(validUsername, context)).isTrue();
        verifyNoInteractions(context);
    }

    @ParameterizedTest
    @ValueSource(strings = {"user@name", "us-er123", "a1b2;c3", "user!", "USER", "user name"})
    void isValid_shouldReturnFalseForInvalidUsernames(String invalidUsername) {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);

        assertThat(validator.isValid(invalidUsername, context)).isFalse();

        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate(
                "Username can only contain lowercase letters and numbers (a-z, 0-9)");
        verify(builder).addConstraintViolation();
    }


}
