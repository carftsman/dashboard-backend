package com.dhatvibs.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.dashboard.entity.ColumnAlias;

public interface ColumnAliasRepository extends JpaRepository<ColumnAlias, Long> {

    Optional<ColumnAlias> findByAlias(String alias);

}