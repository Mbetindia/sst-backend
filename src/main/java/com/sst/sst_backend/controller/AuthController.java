package com.sst.sst_backend.controller;

import com.sst.sst_backend.dto.AuthResponse;
import com.sst.sst_backend.dto.LoginRequest;
import com.sst.sst_backend.dto.RegisterRequest;
import com.sst.sst_backend.dto.StudentLoginRequest;
import com.sst.sst_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/student-login")
    public ResponseEntity<AuthResponse> studentLogin(
            @Valid @RequestBody StudentLoginRequest request
    ) {
        AuthResponse response = authService.studentLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/teacher-login")
    public ResponseEntity<AuthResponse> teacherLogin(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.teacherLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin-login")
    public ResponseEntity<AuthResponse> adminLogin(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.adminLogin(request);
        return ResponseEntity.ok(response);
    }
}