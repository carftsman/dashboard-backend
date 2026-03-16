package com.dhatvibs.dashboard.mapper;

import com.dhatvibs.dashboard.dto.UserRequestDTO;
import com.dhatvibs.dashboard.dto.UserResponseDTO;
import com.dhatvibs.dashboard.entity.User;

public class UserMapper {

    // Convert RequestDTO → Entity
    public static User toEntity(UserRequestDTO dto) {

        User user = new User();

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setProfilePicture(dto.getProfilePicture());

        return user;
    }

    // Convert Entity → ResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isActive(user.getIsActive())
                .build();
    }

}