package com.dhatvibs.dashboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dashboard_stats")
public class DashboardStats {

    @Id
    private Long id;

}