package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.AuthResponse;
import com.sst.sst_backend.dto.LoginRequest;
import com.sst.sst_backend.dto.RegisterRequest;
import com.sst.sst_backend.dto.StudentLoginRequest;
import com.sst.sst_backend.entity.User;
import com.sst.sst_backend.enums.Role;
import com.sst.sst_backend.exception.EmailAlreadyExistsException;
import com.sst.sst_backend.exception.InvalidCredentialsException;
import com.sst.sst_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.STUDENT;

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                request.getClassName(),
                "Registration successful"
        );
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password")
                );

        validatePassword(request.getPassword(), user.getPassword());

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                null,
                "Login successful"
        );
    }

    public AuthResponse studentLogin(StudentLoginRequest request) {

        User user = userRepository.findByEmailAndRole(request.getEmail(), Role.STUDENT)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid student login credentials")
                );

        validatePassword(request.getPassword(), user.getPassword());

        if (!user.getName().equalsIgnoreCase(request.getName().trim())) {
            throw new InvalidCredentialsException("Invalid student name");
        }

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                request.getClassName(),
                "Student login successful"
        );
    }

    public AuthResponse teacherLogin(LoginRequest request) {

        User user = userRepository.findByEmailAndRole(request.getEmail(), Role.TEACHER)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid teacher login credentials")
                );

        validatePassword(request.getPassword(), user.getPassword());

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                null,
                "Teacher login successful"
        );
    }

    public AuthResponse adminLogin(LoginRequest request) {

        User user = userRepository.findByEmailAndRole(request.getEmail(), Role.ADMIN)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid admin login credentials")
                );

        validatePassword(request.getPassword(), user.getPassword());

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                null,
                "Admin login successful"
        );
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }
}