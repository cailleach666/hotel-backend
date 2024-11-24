package org.example.backend.configs;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGeneratorUtil {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        String encodedKey = java.util.Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated Base64 Encoded Secret Key: " + encodedKey);
    }
}

