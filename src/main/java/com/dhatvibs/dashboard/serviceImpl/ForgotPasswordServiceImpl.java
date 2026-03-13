package com.dhatvibs.dashboard.serviceImpl;

import com.dhatvibs.dashboard.entity.User;
import com.dhatvibs.dashboard.repository.UserRepository;
import com.dhatvibs.dashboard.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;

    @Override
    public String sendResetLink(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return "Email verified. Please reset your password.";
    }

    @Override
    public String resetPassword(String email, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

      
        user.setPassword(newPassword);

        userRepository.save(user);

        return "Password updated successfully";
    }
}