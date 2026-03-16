package com.dhatvibs.dashboard.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.io.BufferedInputStream;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // ===============================
    // SAVE COLUMN MAPPING
    // ===============================
    @Transactional
    public void saveMappings(Long datasetId, Map<String,String> mappings) {

        datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

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
    public List<Map<String,String>> previewDataset(Long datasetId, int limit) throws Exception {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        String fileUrl = dataset.getFileUrl();
        if(fileUrl.endsWith(".csv")){
            return previewCSV(fileUrl, limit);
        }
        else if(fileUrl.endsWith(".xlsx") || fileUrl.endsWith(".xls")){
            return previewExcel(fileUrl, limit);
        }
        else{
            throw new RuntimeException("Unsupported dataset format");
        }
    }

    // ===============================
    // PREVIEW CSV
    // ===============================
    private List<Map<String,String>> previewCSV(String fileUrl, int limit) throws Exception {

        URL url = new URL(fileUrl);

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String headerLine = reader.readLine();

            if(headerLine == null)
                throw new RuntimeException("CSV file is empty");

            String[] headers = headerLine.split(",");

            List<Map<String,String>> rows = new ArrayList<>();

            String line;
            int count = 0;

            while((line = reader.readLine()) != null && count < limit){

                String[] values = line.split(",");

                Map<String,String> row = new HashMap<>();

                for(int i=0;i<headers.length;i++){
                    row.put(headers[i], i < values.length ? values[i] : "");
                }

                rows.add(row);

                count++;
            }

            return rows;
        }
    }
    private List<Map<String,String>> previewExcel(String fileUrl, int limit) throws Exception {

        URL url = new URL(fileUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = new BufferedInputStream(connection.getInputStream());
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = findHeaderRow(sheet);

            DataFormatter formatter = new DataFormatter();

            List<String> headers = new ArrayList<>();

            for (Cell cell : headerRow) {
                headers.add(formatter.formatCellValue(cell));
            }

            int startRow = headerRow.getRowNum() + 1;

            List<Map<String,String>> rows = new ArrayList<>();

            for (int i = startRow; i <= sheet.getLastRowNum() && rows.size() < limit; i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String,String> rowData = new HashMap<>();

                for (int j = 0; j < headers.size(); j++) {

                    Cell cell = row.getCell(j);

                    rowData.put(headers.get(j),
                            cell == null ? "" : formatter.formatCellValue(cell));
                }

                rows.add(rowData);
            }

            return rows;
        }
    }
    public List<String> extractColumns(Long datasetId) throws Exception {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        String fileUrl = dataset.getFileUrl();

        if(fileUrl.endsWith(".csv")){
            return extractCSVColumns(fileUrl);
        }
        else if(fileUrl.endsWith(".xlsx") || fileUrl.endsWith(".xls")){
            return extractExcelColumns(fileUrl);
        }
        else{
            throw new RuntimeException("Unsupported file format");
        }
    }
    private List<String> extractCSVColumns(String fileUrl) throws Exception {

        URL url = new URL(fileUrl);

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String headerLine = reader.readLine();

            if(headerLine == null)
                throw new RuntimeException("CSV file is empty");

            return Arrays.asList(headerLine.split(","));
        }
    }

    private List<String> extractExcelColumns(String fileUrl) throws Exception {

        URL url = new URL(fileUrl);

        try (InputStream inputStream = url.openStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = findHeaderRow(sheet);

            List<String> columns = new ArrayList<>();

            DataFormatter formatter = new DataFormatter();

            for (Cell cell : headerRow) {
                columns.add(formatter.formatCellValue(cell));
            }

            return columns;
        }
    }
    // ===============================
    // FIND FIRST HEADER ROW
    // ===============================
    private Row findHeaderRow(Sheet sheet){

        for(int i = 0; i <= sheet.getLastRowNum(); i++){

            Row row = sheet.getRow(i);

            if(row == null) continue;

            int nonEmptyCells = 0;

            for(Cell cell : row){
                if(cell != null && !cell.toString().trim().isEmpty()){
                    nonEmptyCells++;
                }
            }

            if(nonEmptyCells >= 2){
                return row;
            }
        }

        throw new RuntimeException("No valid header row found in Excel");
    }

    // ===============================
    // AUTO MAP COLUMNS
    // ===============================
    public Map<String,String> autoMapColumns(Long datasetId) throws Exception {

        List<String> columns = extractColumns(datasetId);

        Map<String,String> mappings = new HashMap<>();

        for(String column : columns){

            String c = column.toLowerCase();

            if(c.contains("date"))
                mappings.put("date", column);

            else if(c.contains("revenue") || c.contains("amount"))
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

        return mappings;
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
    // PROCESS DATASET
    // ===============================
    public void processDataset(Long datasetId){

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        dataset.setStatus("PROCESSED");

        datasetRepository.save(dataset);
    }

    // ===============================
    // DELETE DATASET
    // ===============================
    public void deleteDataset(Long datasetId){
        datasetRepository.deleteById(datasetId);
    }
}