package com.dhatvibs.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreviewRequest {

    private Long datasetId;

    private int limit;

}