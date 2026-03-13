package com.dhatvibs.dashboard.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dhatvibs.dashboard.repository.DatasetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FileParsingService fileParsingService;
    private final DatasetRepository datasetRepository;

    public Map<String,Object> generateDashboard(Long datasetId, String type) {

        if(type.equals("ROI"))
            return generateRoiDashboard(datasetId);

        if(type.equals("VENDOR"))
            return generateVendorDashboard(datasetId);

        if(type.equals("CUSTOMER"))
            return generateCustomerDashboard(datasetId);

        if(type.equals("RIDER"))
            return generateRiderDashboard(datasetId);

        if(type.equals("SALES"))
            return generateSalesDashboard(datasetId);

        throw new RuntimeException("Unknown dashboard type");
    }
}