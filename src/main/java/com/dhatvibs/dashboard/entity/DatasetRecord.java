package com.dhatvibs.dashboard.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dataset_records")
public class DatasetRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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