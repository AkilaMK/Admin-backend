package com.example.admin.controller;

import com.example.admin.data.model.User;
import com.example.admin.dto.request.RegisterRequest;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = userService.registerUser(registerRequest);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUserByAdmin(@RequestBody RegisterRequest registerRequest, @RequestParam String roleName) {
        User user = userService.createNewUser(registerRequest, roleName);

        return ResponseEntity.ok("User created successfully");
    }
}
