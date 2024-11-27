package com.boltie.backend.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class AppExceptionTest {

    @Test
    void testHttpStatusShouldSucceed() {
        AppException appException = assertThrows(AppException.class, () -> {
            throw new AppException("message", HttpStatus.BAD_REQUEST);
        });

        assertEquals("message", appException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, appException.getHttpStatus());
    }

}