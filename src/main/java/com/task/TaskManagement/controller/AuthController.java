package com.task.TaskManagement.controller;

import com.task.TaskManagement.dto.AuthResponseDTO;
import com.task.TaskManagement.model.User;
import com.task.TaskManagement.repository.UserRepository;
import com.task.TaskManagement.security.JwtUtil;
import com.task.TaskManagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Tag(name = "Authentication", description = "Handles user authentication and registration")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Resister a new user")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthResponseDTO user) {
        return ResponseEntity.ok(authService.register(user));
    }
    @Operation(summary = "Login user and return JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthResponseDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "refresh user token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody AuthResponseDTO request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}
