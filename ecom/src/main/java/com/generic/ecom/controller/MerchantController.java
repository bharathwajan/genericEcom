package com.generic.ecom.controller;

import com.generic.ecom.DTOs.DownloadProductImageDTO;
import com.generic.ecom.DTOs.UploadProductImageDTO;
import com.generic.ecom.view.Services.AWSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MerchantController {

    private final AWSService storageService;

    public MerchantController(AWSService storageService) {
        this.storageService = storageService;
    }


    @PostMapping("/upload-product-photo")
    public ResponseEntity<Map<String, String>> getUploadUrl(
            @RequestBody UploadProductImageDTO request) {
        String presignedUrl = storageService.generateUploadUrl(request.fileName());
        return ResponseEntity.ok(Map.of("uploadUrl", presignedUrl));
    }

    /**
     * GET /api/v1/storage/download-url/{bucketKey}?fileName=my-video.mp4
     * Example: /api/v1/storage/download-url/image?fileName=profile-pic.jpg
     */
    @PostMapping("/download-product-photo")
    public ResponseEntity<Map<String, String>> getDownloadUrl(@RequestBody DownloadProductImageDTO request) {
        String presignedUrl = storageService.generateDownloadUrl(request.fileName());
        return ResponseEntity.ok(Map.of("downloadUrl", presignedUrl));
    }
}