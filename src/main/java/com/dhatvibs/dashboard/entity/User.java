package com.dhatvibs.dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users", schema = "public")   
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true, nullable = false)   
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;
}