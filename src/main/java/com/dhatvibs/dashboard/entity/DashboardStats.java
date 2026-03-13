package com.dhatvibs.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dashboard_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dashboardName;

    private String description;

    private String previewImage;

}