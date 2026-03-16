package com.dhatvibs.dashboard.service;

import java.util.List;

import com.dhatvibs.dashboard.dto.DashboardRequestDTO;
import com.dhatvibs.dashboard.dto.DashboardResponseDTO;

public interface DashboardService {

    List<DashboardResponseDTO> getAllDashboards();

    DashboardResponseDTO getDashboardById(Long id);

    DashboardResponseDTO createDashboard(DashboardRequestDTO requestDTO);

    DashboardResponseDTO updateDashboard(Long id, DashboardRequestDTO requestDTO);

    void deleteDashboard(Long id);
}