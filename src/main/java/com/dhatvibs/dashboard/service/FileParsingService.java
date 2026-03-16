package com.dhatvibs.dashboard.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;

@Service
public class FileParsingService {

    public String[] extractCsvColumns(String fileUrl) {

        try {

            URL url = new URL(fileUrl);

            CSVReader reader =
                    new CSVReader(new InputStreamReader(url.openStream()));

            String[] headers = reader.readNext();

            reader.close();

            return headers;

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract CSV columns", e);
        }
    }


public List<Map<String,String>> previewCsv(String fileUrl, int limit) {

    List<Map<String,String>> rows = new ArrayList<>();

    try {

        URL url = new URL(fileUrl);

        CSVReader reader =
                new CSVReader(new InputStreamReader(url.openStream()));

        String[] headers = reader.readNext();

        String[] values;

        int count = 0;

        while((values = reader.readNext()) != null && count < limit){

            Map<String,String> row = new HashMap<>();

            for(int i=0;i<headers.length;i++){

                String value = i < values.length ? values[i] : "";

                row.put(headers[i], value);
            }

            rows.add(row);
            count++;
        }

        reader.close();

        return rows;

    } catch (Exception e) {

        throw new RuntimeException("Preview failed", e);
    }
}
}