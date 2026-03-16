package com.dhatvibs.dashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.dhatvibs.dashboard.dto.DatasetRecordDTO;
import com.dhatvibs.dashboard.entity.DatasetRecord;
import com.dhatvibs.dashboard.service.DatasetRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dataset-records")
@RequiredArgsConstructor
public class DatasetRecordController {

    private final DatasetRecordService datasetRecordService;

    @PostMapping
    public DatasetRecord saveRecord(@RequestBody DatasetRecordDTO dto) {

        return datasetRecordService.saveRecord(dto);
    }

    @PostMapping("/bulk")
    public List<DatasetRecord> saveBulk(@RequestBody List<DatasetRecordDTO> records) {

        return datasetRecordService.saveAll(records);
    }

    @GetMapping("/{datasetId}")
    public List<DatasetRecord> getRecords(@PathVariable Long datasetId) {

        return datasetRecordService.getByDatasetId(datasetId);
    }
}