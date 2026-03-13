package com.dhatvibs.dashboard.service;

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

    // SAVE COLUMN MAPPING
    @Transactional
    public void saveMappings(Long datasetId, Map<String,String> mappings) {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

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

    // PREVIEW DATASET
    public List<Map<String,String>> previewDataset(PreviewRequest request) throws Exception {

        return fileParsingService.previewCsv(
                request.getFileUrl(),
                request.getLimit()
        );
    }

    // AUTO MAP COLUMNS
   public Map<String,String> autoMapColumns(String fileUrl) {

    try {

        String[] columns = fileParsingService.extractCsvColumns(fileUrl);

        Map<String,String> mappings = new HashMap<>();

        for(String column : columns){

            String normalized = column
                    .toLowerCase()
                    .replace(" ", "_");

            if(normalized.contains("date"))
                mappings.put("date", column);

            if(normalized.contains("click"))
                mappings.put("clicks", column);

            if(normalized.contains("spend"))
                mappings.put("ad_spend", column);

            if(normalized.contains("campaign"))
                mappings.put("campaign_name", column);
        }

        return mappings;

    } catch (Exception e) {
        throw new RuntimeException("Auto column mapping failed", e);
    }
}

    // VALIDATE DATASET
    public Map<String,Object> validateDataset(Long datasetId){

        Map<String,Object> result = new HashMap<>();

        result.put("totalRecords",15240);
        result.put("errors",12);
        result.put("warnings",3);

        return result;
    }

    // FIX ERRORS
    public void fixErrors(Long datasetId){

        // logic to remove duplicates / fix invalid data
        System.out.println("Fixing errors for dataset " + datasetId);
    }

    // PROCESS DATASET
    public void processDataset(Long datasetId){

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        dataset.setStatus("PROCESSED");

        datasetRepository.save(dataset);
    }

    // GET SINGLE DATASET
    public Map<String,Object> getDataset(Long datasetId){

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        Map<String,Object> result = new HashMap<>();

        result.put("datasetId", dataset.getDatasetId());
        result.put("fileName", dataset.getFileName());
        result.put("status", dataset.getStatus());

        return result;
    }

    // GET ALL DATASETS
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

    // DELETE DATASET
    public void deleteDataset(Long datasetId){

        datasetRepository.deleteById(datasetId);
    }

}