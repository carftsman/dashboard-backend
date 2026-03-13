package com.dhatvibs.dashboard.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dhatvibs.dashboard.service.AzureBlobService;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private AzureBlobService azureBlobService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        return azureBlobService.uploadFile(file);
    }
}