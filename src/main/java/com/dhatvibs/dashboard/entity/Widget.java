package com.dhatvibs.dashboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dashboardId;

    private String title;

    private String chartType;

    private String metric;

    private String groupBy;

    private Long datasetId;

}