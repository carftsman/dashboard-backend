package com.dhatvibs.dashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.dhatvibs.dashboard.dto.DashboardRequestDTO;
import com.dhatvibs.dashboard.dto.DashboardResponseDTO;
import com.dhatvibs.dashboard.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboards")
@RequiredArgsConstructor
@Tag(name = "Dashboard APIs", description = "Dashboard Selection APIs")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Get all dashboards")
    public List<DashboardResponseDTO> getAllDashboards() {

        return dashboardService.getAllDashboards();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get dashboard by ID")
    public DashboardResponseDTO getDashboardById(@PathVariable Long id) {

        return dashboardService.getDashboardById(id);
    }

    @PostMapping
    @Operation(summary = "Create new dashboard")
    public DashboardResponseDTO createDashboard(@RequestBody DashboardRequestDTO dashboard) {

        return dashboardService.createDashboard(dashboard);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update dashboard")
    public DashboardResponseDTO updateDashboard(
            @PathVariable Long id,
            @RequestBody DashboardRequestDTO dashboard) {

        return dashboardService.updateDashboard(id, dashboard);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete dashboard")
    public String deleteDashboard(@PathVariable Long id) {

        dashboardService.deleteDashboard(id);

        return "Dashboard deleted successfully";
    }
}