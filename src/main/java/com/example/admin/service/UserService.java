package com.example.admin.service;

import com.example.admin.data.model.Role;
import com.example.admin.data.model.User;
import com.example.admin.data.repository.UserRepository;
import com.example.admin.dto.request.RegisterRequest;
import com.example.admin.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User registerUser(RegisterRequest registerRequest) {
        return createNewUser(registerRequest, "USER");
    }

    public User createNewUser(RegisterRequest registerRequest, String roleName) {
        validationService.validateRegisterRequest(registerRequest);

        if (findByEmail(registerRequest.getEmail()) != null) {
            throw new BadRequestException("Email is already in use.");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        // Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        // Set default role
        Role role = new Role();
        role.setName(roleName);
//        role.setUser(user);
        user.getRoles().add(role);

        user = save(user);
        return user;
    }
}
