package com.dhatvibs.dashboard.controller;

import com.dhatvibs.dashboard.dto.LoginRequest;
import com.dhatvibs.dashboard.dto.LoginResponse;
import com.dhatvibs.dashboard.dto.UserResponseDTO;
import com.dhatvibs.dashboard.entity.User;
import com.dhatvibs.dashboard.repository.UserRepository;
import com.dhatvibs.dashboard.service.AuthService;
import com.dhatvibs.dashboard.util.JwtService;
import com.dhatvibs.dashboard.exception.LoginException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/getproFile")
    public UserResponseDTO getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setProfilePicture(user.getProfilePicture());
        response.setTwoFactorEnabled(user.getTwoFactorEnabled());

        return response;
    }

    // Exception handling without global handler
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleLoginException(LoginException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", 400);
        error.put("timestamp", LocalDateTime.now());

        return error;
    }
}