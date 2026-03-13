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
import com.dhatvibs.dashboard.dto.EditProfileDTO;
import com.dhatvibs.dashboard.service.EditProfileService;
import com.dhatvibs.dashboard.service.ForgotPasswordService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EditProfileService editProfileService;
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/getproFile")
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
    
    @PatchMapping("/edit-profile")
    public UserResponse editProfile(Authentication authentication,
                                    @RequestBody EditProfileDTO request) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = editProfileService.editProfile(user.getId(), request);

        UserResponse response = new UserResponse();
        response.setId(updatedUser.getId());
        response.setFullName(updatedUser.getFullName());
        response.setEmail(updatedUser.getEmail());
        response.setRole(updatedUser.getRole().name());
        response.setProfilePicture(updatedUser.getProfilePicture());
        response.setTwoFactorEnabled(updatedUser.getTwoFactorEnabled());

        return response;
    }
   
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword){

        return forgotPasswordService.resetPassword(email, newPassword, confirmPassword);
    }
}