package com.example.admin.tools;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HMACSHA512");
        keyGenerator.init(512); // 512 bits = 64 bytes
        Key key = keyGenerator.generateKey();
        String jwtSecret = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println(jwtSecret);
    }
}
