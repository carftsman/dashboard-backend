package com.dhatvibs.dashboard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhatvibs.dashboard.dto.DatasetRequest;
import com.dhatvibs.dashboard.dto.ExtractColumnsRequest;
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

    @PostMapping("/extract-columns")
    public Map<String,Object> extractColumns(@RequestBody DatasetRequest request) throws Exception {

        return datasetService.extractColumns(request.getDatasetId());

    }

    @PostMapping("/save-mapping")
    public Map<String,String> saveMapping(@RequestBody SaveMappingRequest request){

        datasetService.saveMappings(
                request.getDatasetId(),
                request.getMappings()
        );

        return Map.of("status","mapping_saved");
    }
    @PostMapping("/auto-map")
    public Map<String,Object> autoMap(@RequestBody DatasetRequest request) throws Exception {

        Map<String,String> mappings =
                datasetService.autoMapColumns(request.getDatasetId());

        return Map.of(
                "status","success",
                "mappings", mappings
        );
    }
    
    @PostMapping("/preview")
    public Map<String,Object> preview(@RequestBody PreviewRequest request) throws Exception {

        List<Map<String,String>> rows =
                datasetService.previewDataset(request);

        return Map.of(
                "status","success",
                "rows", rows
        );
    }
}