package com.boltie.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackendApplicationTest {

    @Test
    void testApplicationStartup() {
        BackendApplication.main(new String[] {});
    }
}