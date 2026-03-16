package com.dhatvibs.dashboard.service;

import java.util.List;

import com.dhatvibs.dashboard.dto.DatasetRecordDTO;
import com.dhatvibs.dashboard.entity.DatasetRecord;

public interface DatasetRecordService {

    DatasetRecord saveRecord(DatasetRecordDTO dto);

    List<DatasetRecord> saveAll(List<DatasetRecordDTO> records);

    List<DatasetRecord> getByDatasetId(Long datasetId);

}