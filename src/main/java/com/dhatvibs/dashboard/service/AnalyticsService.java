package com.dhatvibs.dashboard.service;

import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final JdbcTemplate jdbcTemplate;

    public Map<String,Object> runQuery(
            Long datasetId,
            String metric,
            String groupBy
    ){

        String sql = """
            SELECT record_json->>? as label,
                   SUM((record_json->>?)::numeric) as value
            FROM dataset_records
            WHERE dataset_id = ?
            GROUP BY label
        """;

        List<Map<String,Object>> rows =
            jdbcTemplate.queryForList(sql, groupBy, metric, datasetId);

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for(Map<String,Object> row : rows){
            labels.add(row.get("label").toString());
            values.add(Double.valueOf(row.get("value").toString()));
        }

        return Map.of(
                "labels", labels,
                "values", values
        );
    }

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