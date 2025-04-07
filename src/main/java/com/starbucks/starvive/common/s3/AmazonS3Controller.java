package com.starbucks.starvive.common.s3;

import com.starbucks.starvive.common.domain.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/images")
@RestController
@RequiredArgsConstructor
public class AmazonS3Controller {

    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public BaseResponseEntity<List<String>> uploadProductImages(@RequestPart("imageFiles") List<MultipartFile> images) {
        List<String> imageUrls = s3Uploader.uploadMultiple(images, "products");
        return new BaseResponseEntity<>(imageUrls);
    }
}
