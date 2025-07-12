package com.dadam.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestDecrypt {

    @Value("${portone.imp_key}")
    String impKey;

    @Test
    void testDecrypt() {
        System.out.println("복호화된 impKey: " + impKey);
    }
}
