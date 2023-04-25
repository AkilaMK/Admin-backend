package com.example.admin.service;

import com.example.admin.dto.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String authenticateUser(LoginRequest loginRequest) throws Exception {
        // Validate login request
        validationService.validateLoginRequest(loginRequest);

        // Authenticate user using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Set authenticated user in SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token using custom JWT utility class
        String token = jwtService.generateToken(authentication);

        // Log successful authentication
//        auditService.logAuthenticationSuccess(loginRequest.getEmail(), loginRequest.getRemoteAddress());

        return token;
    }
}
