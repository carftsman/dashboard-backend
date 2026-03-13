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
@Table(name = "system_columns")
public class SystemColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    private Long dashboardId;

    private String systemColumn;

    private String dataType;

}