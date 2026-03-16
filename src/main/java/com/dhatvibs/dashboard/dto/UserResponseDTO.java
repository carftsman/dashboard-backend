package com.dhatvibs.dashboard.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String profilePicture;
    private boolean twoFactorEnabled;
}