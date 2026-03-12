package com.dhatvibs.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhatvibs.dashboard.entity.DashboardStats;

@Repository
public interface DashboardRepository extends JpaRepository<DashboardStats, Long> {
}