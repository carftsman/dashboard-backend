package com.dhatvibs.dashboard.service;

import java.util.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    public Map<String,Object> getWidgetData(
            Long datasetId,
            String metric,
            String groupBy
    ){

        // Example mock data
        List<String> labels = List.of(
                "2026-03-01",
                "2026-03-02",
                "2026-03-03"
        );

        List<Integer> values = List.of(
                1200,
                1500,
                1800
        );

        Map<String,Object> result = new HashMap<>();

        result.put("labels", labels);
        result.put("values", values);

        return result;
    }
}