package com.example.admin.controller;

import com.example.admin.dto.request.LoginRequest;
import com.example.admin.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            String jwt = authenticationService.authenticateUser(loginRequest);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

