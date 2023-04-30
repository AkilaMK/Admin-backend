package com.example.admin.controller;

import com.example.admin.dto.UserDto;
import com.example.admin.dto.request.RegisterRequest;
import com.example.admin.dto.response.ApiResponse;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);

        ApiResponse<?> response = new ApiResponse<>(HttpStatus.CREATED.value(), "User registered successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUserByAdmin(@RequestBody RegisterRequest registerRequest, @RequestParam String roleName) {
        userService.createNewUser(registerRequest, roleName);

        ApiResponse<?> response = new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDto> users = userService.getAllUsers();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", users));
    }
}
