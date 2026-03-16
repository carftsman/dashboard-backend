package com.dhatvibs.dashboard.service;

import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dhatvibs.dashboard.entity.Dataset;
import com.dhatvibs.dashboard.entity.DatasetRecord;
import com.dhatvibs.dashboard.repository.DatasetRecordRepository;
import com.dhatvibs.dashboard.repository.DatasetRepository;
import com.opencsv.CSVReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsvDatasetImportService {

    private final DatasetRecordRepository datasetRecordRepository;
    private final DatasetRepository datasetRepository;

    public void importCsv(Long datasetId) throws Exception {

        Dataset dataset = datasetRepository.findById(datasetId)
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        String fileUrl = dataset.getFileUrl();

        URL url = new URL(fileUrl);

        CSVReader reader = new CSVReader(new InputStreamReader(url.openStream()));

        reader.readNext(); // skip header

        String[] row;

        List<DatasetRecord> records = new ArrayList<>();

        while ((row = reader.readNext()) != null) {

            DatasetRecord record = new DatasetRecord();

            record.setDatasetId(datasetId);

            record.setCampaignId(row[0]);
            record.setCampaignName(row[1]);
            record.setPlatform(row[2]);

            record.setDate(LocalDate.parse(row[3]));

            record.setImpressions(Integer.parseInt(row[4]));
            record.setClicks(Integer.parseInt(row[5]));
            record.setLeads(Integer.parseInt(row[6]));
            record.setOrders(Integer.parseInt(row[7]));

            record.setRevenue(Double.parseDouble(row[8]));
            record.setAdSpend(Double.parseDouble(row[9]));

            records.add(record);
        }

        datasetRecordRepository.saveAll(records);

        reader.close();
    }
}