package com.dhatvibs.dashboard.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dhatvibs.dashboard.dto.DashboardRequestDTO;
import com.dhatvibs.dashboard.dto.DashboardResponseDTO;
import com.dhatvibs.dashboard.entity.DashboardStats;
import com.dhatvibs.dashboard.exception.DashboardException;
import com.dhatvibs.dashboard.repository.DashboardRepository;
import com.dhatvibs.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    public List<DashboardResponseDTO> getAllDashboards() {
        return dashboardRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DashboardResponseDTO getDashboardById(Long id) {
        DashboardStats dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new DashboardException(
                        "Dashboard not found with id: " + id, HttpStatus.NOT_FOUND));

        return convertToDTO(dashboard);
    }

    @Override
    public DashboardResponseDTO createDashboard(DashboardRequestDTO requestDTO) {
        DashboardStats dashboard = convertToEntity(requestDTO);
        DashboardStats savedDashboard = dashboardRepository.save(dashboard);
        return convertToDTO(savedDashboard);
    }

    @Override
    public DashboardResponseDTO updateDashboard(Long id, DashboardRequestDTO requestDTO) {
        DashboardStats dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new DashboardException(
                        "Dashboard not found with id: " + id, HttpStatus.NOT_FOUND));

        dashboard.setDashboardName(requestDTO.getDashboardName());
        dashboard.setDescription(requestDTO.getDescription());
        dashboard.setPreviewImage(requestDTO.getPreviewImage());

        DashboardStats updatedDashboard = dashboardRepository.save(dashboard);
        return convertToDTO(updatedDashboard);
    }

    @Override
    public void deleteDashboard(Long id) {
        DashboardStats dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new DashboardException(
                        "Dashboard not found with id: " + id, HttpStatus.NOT_FOUND));

        dashboardRepository.delete(dashboard);
    }

    // Utility: convert entity to DTO
    private DashboardResponseDTO convertToDTO(DashboardStats dashboard) {
        return DashboardResponseDTO.builder()
                .id(dashboard.getId())
                .dashboardName(dashboard.getDashboardName())
                .description(dashboard.getDescription())
                .previewImage(dashboard.getPreviewImage())
                .build();
    }

    // Utility: convert DTO to entity
    private DashboardStats convertToEntity(DashboardRequestDTO requestDTO) {
        return DashboardStats.builder()
                .dashboardName(requestDTO.getDashboardName())
                .description(requestDTO.getDescription())
                .previewImage(requestDTO.getPreviewImage())
                .build();
    }
}