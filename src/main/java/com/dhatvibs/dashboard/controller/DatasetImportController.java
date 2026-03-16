package com.dhatvibs.dashboard.controller;

import org.springframework.web.bind.annotation.*;

import com.dhatvibs.dashboard.service.CsvDatasetImportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/datasets")
@RequiredArgsConstructor
public class DatasetImportController {

    private final CsvDatasetImportService csvDatasetImportService;

    @PostMapping("/import")
    public String importDataset(@RequestParam Long datasetId) throws Exception {

        csvDatasetImportService.importCsv(datasetId);

        return "Dataset imported successfully";
    }
}