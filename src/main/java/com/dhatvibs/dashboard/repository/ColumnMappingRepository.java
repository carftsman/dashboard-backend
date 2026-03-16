package com.dhatvibs.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.dashboard.entity.ColumnMapping;

public interface ColumnMappingRepository extends JpaRepository<ColumnMapping, Long> {
	void deleteByDatasetId(Long datasetId);
}