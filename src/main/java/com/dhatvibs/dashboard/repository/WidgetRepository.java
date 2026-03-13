package com.dhatvibs.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.dashboard.entity.Widget;

public interface WidgetRepository extends JpaRepository<Widget, Long> {

}