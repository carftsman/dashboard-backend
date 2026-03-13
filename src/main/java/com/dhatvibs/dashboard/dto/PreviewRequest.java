package com.dhatvibs.dashboard.dto;

import lombok.Data;

@Data
public class PreviewRequest {

    private Long datasetId;
    private String fileUrl;
    private int limit;

}