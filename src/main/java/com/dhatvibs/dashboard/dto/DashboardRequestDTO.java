package com.dhatvibs.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardRequestDTO {

    private String dashboardName;

    private String description;

    private String previewImage;

}