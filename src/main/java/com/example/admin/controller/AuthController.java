package com.example.admin.controller;

import com.example.admin.dto.response.LoginResponse;
import com.example.admin.exception.UnauthorizedException;
import com.example.admin.service.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${jwt.expiration.web}")
    private long jwtWebExpirationInMils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            String jwt = authenticationService.authenticateUser(authorizationHeader);
            LoginResponse loginResponse = new LoginResponse(jwt, jwtWebExpirationInMils);

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("Failed to login", e);
            throw new UnauthorizedException("Failed to login");
        }
    }
}

