package com.dhatvibs.dashboard.service;

public interface ForgotPasswordService {
	String sendResetLink(String email);
    String resetPassword(String email, String newPassword, String confirmPassword);

}