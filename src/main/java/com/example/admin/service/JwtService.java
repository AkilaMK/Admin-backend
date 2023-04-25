package com.example.admin.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.web}")
    private long jwtWebExpirationInMils;

    public String generateToken(Authentication authentication) {
        // Generate random secret key
        byte[] secretBytes = new byte[64];
        SecureRandom random = new SecureRandom();
        random.nextBytes(secretBytes);
        Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Add custom claims to JWT token
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("roles", userDetails.getRoles());
//        claims.put("userId", userDetails.getUserId());

        // Generate JWT token
        Date expiryDate = new Date(System.currentTimeMillis() + jwtWebExpirationInMils);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
//                .addClaims(claims)
                .signWith(secretKey)
                .compact();
    }
}

