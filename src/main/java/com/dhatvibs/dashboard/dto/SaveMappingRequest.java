package com.dhatvibs.dashboard.dto;

import java.util.Map;

import lombok.Data;

@Data
public class SaveMappingRequest {

    private Long datasetId;

    private Map<String, String> mappings;

}