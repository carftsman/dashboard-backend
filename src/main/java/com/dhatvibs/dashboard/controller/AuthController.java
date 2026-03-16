package com.dhatvibs.dashboard.controller;

import com.dhatvibs.dashboard.dto.EditProfileDTO;
import com.dhatvibs.dashboard.dto.LoginRequest;
import com.dhatvibs.dashboard.dto.LoginResponse;
import com.dhatvibs.dashboard.dto.UserResponseDTO;
import com.dhatvibs.dashboard.entity.User;
import com.dhatvibs.dashboard.exception.UserNotFoundException;
import com.dhatvibs.dashboard.repository.UserRepository;
import com.dhatvibs.dashboard.service.AuthService;
import com.dhatvibs.dashboard.service.EditProfileService;
import com.dhatvibs.dashboard.service.ForgotPasswordService;
import com.dhatvibs.dashboard.util.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication APIs", description = "Login, profile, and password management APIs")
public class AuthController {

    private final AuthService authService;
    @SuppressWarnings("unused")
	private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EditProfileService editProfileService;
    private final ForgotPasswordService forgotPasswordService;

    // Login API
    @PostMapping("/login")
    @Operation(summary = "User login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // Get Logged-in User Profile
    @GetMapping("/profile")
    @Operation(summary = "Get current logged-in user profile")
    public UserResponseDTO getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setProfilePicture(user.getProfilePicture());

        return response;
    }

    // Edit Profile
    @PatchMapping("/edit-profile")
    @Operation(summary = "Edit user profile")
    public UserResponseDTO editProfile(Authentication authentication,
                                    @RequestBody EditProfileDTO request) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User updatedUser = editProfileService.editProfile(user.getId(), request);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(updatedUser.getId());
        response.setFullName(updatedUser.getFullName());
        response.setEmail(updatedUser.getEmail());
        response.setRole(updatedUser.getRole());
        response.setProfilePicture(updatedUser.getProfilePicture());

        return response;
    }

    // Reset Password
    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword) {

        return forgotPasswordService.resetPassword(email, newPassword, confirmPassword);
    }
}