package com.dhatvibs.dashboard.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.dhatvibs.dashboard.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/widget-data")
    public Map<String,Object> getWidgetData(

            @RequestParam Long datasetId,
            @RequestParam String metric,
            @RequestParam(required=false) String groupBy

    ){

        return analyticsService.getWidgetData(datasetId, metric, groupBy);
    }

}