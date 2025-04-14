package com.boltie.backend.annotations;

import com.boltie.backend.validators.UsernameBlacklistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameBlacklistValidator.class)
@Documented
public @interface NotBlacklisted {
    String message() default "Username is not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
