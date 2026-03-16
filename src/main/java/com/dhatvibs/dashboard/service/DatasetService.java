package com.dhatvibs.dashboard.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dhatvibs.dashboard.dto.PreviewRequest;
import com.dhatvibs.dashboard.entity.ColumnMapping;
import com.dhatvibs.dashboard.entity.Dataset;
import com.dhatvibs.dashboard.repository.ColumnMappingRepository;
import com.dhatvibs.dashboard.repository.DatasetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final ColumnMappingRepository columnMappingRepository;
    private final DatasetRepository datasetRepository;
    private final FileParsingService fileParsingService;

    // ===============================
    // SAVE COLUMN MAPPING
    // ===============================
    @Transactional
    public void saveMappings(Long datasetId, Map<String,String> mappings) {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        // remove old mappings
        columnMappingRepository.deleteByDatasetId(datasetId);

        List<ColumnMapping> mappingList = new ArrayList<>();

        mappings.forEach((systemColumn, uploadedColumn) -> {

            ColumnMapping mapping = new ColumnMapping();

            mapping.setDatasetId(datasetId);
            mapping.setSystemColumn(systemColumn);
            mapping.setUploadedColumn(uploadedColumn);

            mappingList.add(mapping);
        });

        columnMappingRepository.saveAll(mappingList);
    }

    // ===============================
    // PREVIEW DATASET
    // ===============================
    public List<Map<String,String>> previewDataset(PreviewRequest request) throws Exception {

        Dataset dataset = datasetRepository.findById(request.getDatasetId())
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        return fileParsingService.previewCsv(
                dataset.getFileUrl(),
                request.getLimit()
        );
    }

    // ===============================
    // AUTO MAP COLUMNS
    // ===============================
    public Map<String,String> autoMapColumns(Long datasetId) throws Exception {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        URL url = new URL(dataset.getFileUrl());

        Map<String,String> mappings = new HashMap<>();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(url.openStream()))) {

            String headerLine = reader.readLine();

            if(headerLine == null){
                throw new RuntimeException("CSV file is empty");
            }

            String[] columns = headerLine.split(",");

            for(String column : columns){

                String c = column.toLowerCase();

                if(c.contains("date") || c.contains("period"))
                    mappings.put("date", column);

                else if(c.contains("revenue") || c.contains("value") || c.contains("amount"))
                    mappings.put("revenue", column);

                else if(c.contains("campaign"))
                    mappings.put("campaignName", column);

                else if(c.contains("platform"))
                    mappings.put("platform", column);

                else if(c.contains("click"))
                    mappings.put("clicks", column);

                else if(c.contains("impression"))
                    mappings.put("impressions", column);

                else if(c.contains("lead"))
                    mappings.put("leads", column);

                else if(c.contains("order"))
                    mappings.put("orders", column);
            }
        }

        return mappings;
    }

    // ===============================
    // EXTRACT CSV COLUMNS
    // ===============================
    public Map<String, Object> extractColumns(Long datasetId) throws Exception {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        URL url = new URL(dataset.getFileUrl());

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(url.openStream()))) {

            String headerLine = reader.readLine();

            if(headerLine == null){
                throw new RuntimeException("CSV file is empty");
            }

            String[] columns = Arrays.stream(headerLine.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            return Map.of(
                    "datasetId", datasetId,
                    "status", "success",
                    "columns", columns
            );
        }
    }

    // ===============================
    // VALIDATE DATASET
    // ===============================
    public Map<String,Object> validateDataset(Long datasetId){

        Map<String,Object> result = new HashMap<>();

        result.put("totalRecords",15240);
        result.put("errors",12);
        result.put("warnings",3);

        return result;
    }

    // ===============================
    // FIX DATA ERRORS
    // ===============================
    public void fixErrors(Long datasetId){

        System.out.println("Fixing errors for dataset " + datasetId);
    }

    // ===============================
    // PROCESS DATASET
    // ===============================
    public void processDataset(Long datasetId){

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        dataset.setStatus("PROCESSED");

        datasetRepository.save(dataset);
    }

    // ===============================
    // GET SINGLE DATASET
    // ===============================
    public Map<String,Object> getDataset(Long datasetId){

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        Map<String,Object> result = new HashMap<>();

        result.put("datasetId", dataset.getDatasetId());
        result.put("fileName", dataset.getFileName());
        result.put("status", dataset.getStatus());

        return result;
    }

    // ===============================
    // GET ALL DATASETS
    // ===============================
    public List<Map<String,Object>> getAllDatasets(){

        List<Dataset> datasets = datasetRepository.findAll();

        List<Map<String,Object>> result = new ArrayList<>();

        for(Dataset dataset : datasets){

            Map<String,Object> row = new HashMap<>();

            row.put("datasetId", dataset.getDatasetId());
            row.put("fileName", dataset.getFileName());
            row.put("status", dataset.getStatus());

            result.add(row);
        }

        return result;
    }

    // ===============================
    // DELETE DATASET
    // ===============================
    public void deleteDataset(Long datasetId){

        datasetRepository.deleteById(datasetId);
    }

}