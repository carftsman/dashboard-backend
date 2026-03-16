package com.dhatvibs.dashboard.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.dhatvibs.dashboard.entity.Widget;
import com.dhatvibs.dashboard.repository.WidgetRepository;
import com.dhatvibs.dashboard.service.AnalyticsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final WidgetRepository widgetRepository;
    
    @GetMapping("/{id}/data")
    public Map<String,Object> getWidgetData(@PathVariable Long id){

        Widget widget = widgetRepository.findById(id).orElseThrow();

        return analyticsService.runQuery(
            widget.getDatasetId(),
            widget.getMetric(),
            widget.getGroupBy()
        );
    }
}