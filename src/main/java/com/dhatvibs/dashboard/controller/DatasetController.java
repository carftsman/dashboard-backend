package com.dhatvibs.dashboard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.dhatvibs.dashboard.dto.DatasetRequest;
import com.dhatvibs.dashboard.dto.PreviewRequest;
import com.dhatvibs.dashboard.dto.SaveMappingRequest;
import com.dhatvibs.dashboard.service.DatasetService;
import com.dhatvibs.dashboard.service.FileParsingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/datasets")
@RequiredArgsConstructor
public class DatasetController {

    private final FileParsingService fileParsingService;
    private final DatasetService datasetService;

    // ===============================
    // EXTRACT COLUMNS
    // ===============================
    @PostMapping("/extract-columns")
    public Map<String,Object> extractColumns(@RequestBody DatasetRequest request) throws Exception {

        List<String> columns =
                datasetService.extractColumns(request.getDatasetId());

        return Map.of(
                "status","success",
                "datasetId", request.getDatasetId(),
                "columns", columns
        );
    }

    // ===============================
    // SAVE COLUMN MAPPING
    // ===============================
    @PostMapping("/save-mapping")
    public Map<String,String> saveMapping(@RequestBody SaveMappingRequest request){

        datasetService.saveMappings(
                request.getDatasetId(),
                request.getMappings()
        );

        return Map.of("status","mapping_saved");
    }

    // ===============================
    // AUTO MAP COLUMNS
    // ===============================
    @PostMapping("/auto-map")
    public Map<String,Object> autoMap(@RequestBody DatasetRequest request) throws Exception {

        Map<String,String> mappings =
                datasetService.autoMapColumns(request.getDatasetId());

        return Map.of(
                "status","success",
                "mappings", mappings
        );
    }

    // ===============================
    // PREVIEW DATASET
    // ===============================
    @PostMapping("/preview")
    public Map<String,Object> preview(@RequestBody PreviewRequest request) throws Exception {

        List<Map<String,String>> rows =
                datasetService.previewDataset(
                        request.getDatasetId(),
                        request.getLimit()
                );

        return Map.of(
                "status","success",
                "rows", rows
        );
    }
}