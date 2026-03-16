package com.dhatvibs.dashboard.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DatasetRecordDTO {

    private Long datasetId;

    private String campaignId;

    private String campaignName;

    private String platform;

    private LocalDate date;

    private Integer impressions;

    private Integer clicks;

    private Integer leads;

    private Integer orders;

    private Double revenue;

    private Double adSpend;

}