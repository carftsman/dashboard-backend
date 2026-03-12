package com.dhatvibs.dashboard.service;

import com.dhatvibs.dashboard.dto.LoginRequest;
import com.dhatvibs.dashboard.dto.LoginResponse;
import com.dhatvibs.dashboard.entity.User;
import com.dhatvibs.dashboard.repository.UserRepository;
import com.dhatvibs.dashboard.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getRole().name(),
                user.getEmail()
        );
    }
}