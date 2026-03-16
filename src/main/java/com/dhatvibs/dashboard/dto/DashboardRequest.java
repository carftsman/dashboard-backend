package com.dhatvibs.dashboard.dto;

import lombok.Data;

@Data
public class DashboardRequest {

    private Long datasetId;
    private String dashboardType;

}