package org.example.backend.configs;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Slf4j
public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        String encodedKey = java.util.Base64.getEncoder().encodeToString(keyBytes);
        log.info("Generated Base64 Encoded Secret Key: {}", encodedKey);
    }
}

