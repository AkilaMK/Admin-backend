package com.example.admin.service;

import com.example.admin.dto.request.LoginRequest;
import com.example.admin.dto.request.RegisterRequest;
import com.example.admin.exception.BadRequestException;
import org.apache.commons.validator.routines.EmailValidator;
import org.passay.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(".*(['\";]+|(--)+).*");

    public void validateLoginRequest(LoginRequest loginRequest) {
        if (Objects.isNull(loginRequest)) {
            throw new BadRequestException("Email and password are required.");
        }

        if (!StringUtils.hasText(loginRequest.getEmail())) {
            throw new BadRequestException("Email is required.");
        }

        if (!StringUtils.hasText(loginRequest.getPassword())) {
            throw new BadRequestException("Password is required.");
        }

        String email = loginRequest.getEmail().trim();
        String password = loginRequest.getPassword().trim();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email) || !isSafeString(email) || !isSafeString(password)) {
            throw new BadRequestException("Invalid email or password.");
        }
    }

    public void validateRegisterRequest(RegisterRequest registerRequest) {

        if (!StringUtils.hasText(registerRequest.getName()) || registerRequest.getName().trim().isEmpty()) {
            throw new BadRequestException("Name cannot be empty.");
        }

        if (!StringUtils.hasText(registerRequest.getEmail()) || registerRequest.getEmail().trim().isEmpty()) {
            throw new BadRequestException("Email is required.");
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(registerRequest.getEmail()) || !isSafeString(registerRequest.getEmail())) {
            throw new BadRequestException("Invalid email.");
        }

        if (!StringUtils.hasText(registerRequest.getPhoneNumber()) || registerRequest.getPhoneNumber().trim().isEmpty()) {
            throw new BadRequestException("Phone number is required.");
        }

        if (!isSafeString(registerRequest.getPhoneNumber())) {
            throw new BadRequestException("Invalid phone number.");
        }

        if (!StringUtils.hasText(registerRequest.getPassword()) || registerRequest.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password is required.");
        }

        isValidPassword(registerRequest.getPassword());

        if (!registerRequest.getPassword().equals(registerRequest.getRetypePassword())) {
            throw new BadRequestException("Passwords do not match.");
        }
    }

    private boolean isSafeString(String input) {
        return input != null && !SQL_INJECTION_PATTERN.matcher(input).matches();
    }

    private void isValidPassword(String password) {
        PasswordValidator validator = new PasswordValidator(new LengthRule(8, 16),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1));

        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            List<String> messages = validator.getMessages(result);
            String error = String.join(", ", messages);
            throw new BadRequestException(error);
        }
    }
}
