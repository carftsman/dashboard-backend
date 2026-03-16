package com.dhatvibs.dashboard.entity;

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
@Table(name = "dataset_columns")
public class DatasetColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    private Long datasetId;

    private String columnName;

}