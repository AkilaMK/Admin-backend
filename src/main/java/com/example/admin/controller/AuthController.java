package com.example.admin.controller;

import com.example.admin.dto.request.LoginRequest;
import com.example.admin.dto.response.ApiResponse;
import com.example.admin.dto.response.LoginResponse;
import com.example.admin.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${jwt.expiration.web}")
    private long jwtWebExpirationInMils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            String jwt = authenticationService.authenticateUser(loginRequest);
            LoginResponse loginResponse = new LoginResponse(jwt, jwtWebExpirationInMils);

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

