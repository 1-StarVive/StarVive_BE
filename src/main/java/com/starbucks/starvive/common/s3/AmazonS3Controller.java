package com.starbucks.starvive.common.s3;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RequestMapping("/api/v1/images")
@RestController
@RequiredArgsConstructor
public class AmazonS3Controller {

    private final S3Uploader s3Uploader;

    @Operation(summary = "s3 이미지 업로드",
            description = "s3 이미지 업로드",
            tags = {"amazon-s3-service"})
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadProductImages(@RequestPart("imageFiles") List<MultipartFile> images) {
        List<String> imageUrls = s3Uploader.uploadMultiple(images, "products");
        return ResponseEntity.ok(imageUrls);
    }
}
}