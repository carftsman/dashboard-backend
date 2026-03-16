package com.dhatvibs.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.dashboard.entity.Dataset;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {

}