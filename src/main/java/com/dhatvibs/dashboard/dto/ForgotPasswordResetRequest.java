package com.dhatvibs.dashboard.dto;

import lombok.Data;

@Data
public class ForgotPasswordResetRequest {

    private String token;

    private String newPassword;
}