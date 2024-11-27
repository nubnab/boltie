package com.boltie.backend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterDtoTest {

    @Test
    void testRegisterDtoInitialisation() {
        RegisterDto registerDto = new RegisterDto("username", "password".toCharArray());

        assertEquals("username", registerDto.username());
        assertArrayEquals("password".toCharArray(), registerDto.password());
    }

    @Test
    void testRegisterDtoInitialisationShouldFail() {
        RegisterDto registerDto = new RegisterDto("username", "password".toCharArray());
        RegisterDto registerDto2 = new RegisterDto("username", "password".toCharArray());

        assertNotEquals(registerDto, registerDto2);
    }

}