package com.dhatvibs.dashboard.controller;

import com.dhatvibs.dashboard.dto.LoginRequest;
import com.dhatvibs.dashboard.dto.LoginResponse;
import com.dhatvibs.dashboard.dto.UserResponse;
import com.dhatvibs.dashboard.entity.User;
import com.dhatvibs.dashboard.repository.UserRepository;
import com.dhatvibs.dashboard.service.AuthService;
import com.dhatvibs.dashboard.util.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getprofile")
    public UserResponse getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setProfilePicture(user.getProfilePicture());
        response.setTwoFactorEnabled(user.getTwoFactorEnabled());

        return response;
    }
}