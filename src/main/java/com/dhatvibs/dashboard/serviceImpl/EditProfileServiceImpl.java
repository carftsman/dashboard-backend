package com.dhatvibs.dashboard.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.dashboard.dto.EditProfileDTO;
import com.dhatvibs.dashboard.entity.User;
import com.dhatvibs.dashboard.repository.UserRepository;
import com.dhatvibs.dashboard.service.EditProfileService;

@Service
public class EditProfileServiceImpl implements EditProfileService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User editProfile(Long userId, EditProfileDTO request) {

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fullname
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        // Update profile picture
        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture());
        }

        // Save updated user
        return userRepository.save(user);
    }
}