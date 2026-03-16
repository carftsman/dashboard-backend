package com.dhatvibs.dashboard.dto;

import com.dhatvibs.dashboard.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User response object returned to clients")
public class UserResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Bindhu Badeti")
    private String fullName;

    @Schema(example = "bindhu@gmail.com")
    private String email;

    @Schema(example = "ADMIN")
    private Role role;

    @Schema(example = "profile.jpg")
    private String profilePicture;

    @Schema(example = "2026-03-16T10:30:00")
    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isActive;

}