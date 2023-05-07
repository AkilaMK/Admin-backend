package com.example.admin.service;

import com.example.admin.exception.UnauthorizedException;
import com.example.admin.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AuthenticationService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String authenticateUser(String authorizationHeader) throws Exception {
        // Extract Basic Auth credentials from the Authorization header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            throw new UnauthorizedException("Invalid Login Request");
        }
        String base64Credentials = authorizationHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        String[] parts = credentials.split(":", 2);
        if (parts.length != 2) {
            throw new UnauthorizedException("Invalid Login Request");
        }

        String email = parts[0];
        String password = parts[1];

        // Authenticate user using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Generate JWT token using custom JWT utility class
        String token = jwtService.generateToken(authentication);

        // Log successful authentication
        // auditService.logAuthenticationSuccess(email, request.getRemoteAddr());

        return token;
    }

}
