package com.dhatvibs.dashboard.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dhatvibs.dashboard.entity.Dataset;
import com.dhatvibs.dashboard.repository.DatasetRepository;
import com.dhatvibs.dashboard.service.AzureBlobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final AzureBlobService azureBlobService;
    private final DatasetRepository datasetRepository;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String url = azureBlobService.uploadFile(file);

        Dataset dataset = new Dataset();

        dataset.setFileName(file.getOriginalFilename());
        dataset.setFileUrl(url);
        dataset.setStatus("UPLOADED");
        dataset.setUploadedAt(LocalDateTime.now());

        dataset = datasetRepository.save(dataset);

        return Map.of(
            "datasetId", dataset.getDatasetId(),
            "fileUrl", url,
            "status", "uploaded"
        );
    }
}