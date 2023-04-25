package com.example.admin.tools;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] secretBytes = new byte[32];
        random.nextBytes(secretBytes);
        String jwtSecret = Base64.getEncoder().encodeToString(secretBytes);

        System.out.println(jwtSecret);
    }
}
