package com.dhatvibs.dashboard.dto;

import com.dhatvibs.dashboard.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "User request object used for creating or updating a user")
public class UserRequestDTO {

    @NotBlank(message = "Full name is required")
    @Schema(example = "Bindhu Badeti")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Schema(example = "bindhu@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(example = "password123")
    private String password;

    @Schema(example = "ADMIN")
    private Role role;

    @Schema(example = "profile.jpg")
    private String profilePicture;

}