package com.example.admin.service;

import com.example.admin.data.model.Role;
import com.example.admin.data.model.User;
import com.example.admin.data.repository.RoleRepository;
import com.example.admin.data.repository.UserRepository;
import com.example.admin.dto.UserDto;
import com.example.admin.dto.request.RegisterRequest;
import com.example.admin.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ValidationService validationService;

    private final RoleRepository roleRepository;

    public User registerUser(RegisterRequest registerRequest) {
        return createNewUser(registerRequest, "USER");
    }

    @Transactional
    public User createNewUser(RegisterRequest registerRequest, String roleName) {
        validationService.validateRegisterRequest(registerRequest);

        User userByEmail = userRepository.findByEmail(registerRequest.getEmail());
        if (!Objects.isNull(userByEmail)) {
            throw new BadRequestException("Email is already in use.");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        // Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName(roleName);
        if (Objects.isNull(role)) {
            throw new BadRequestException("Invalid role requested for the user.");
        }
        user.addRole(role);

        user = userRepository.save(user);
        return user;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
