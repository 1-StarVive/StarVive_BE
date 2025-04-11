package com.starbucks.starvive.common.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/images")
@RestController
@RequiredArgsConstructor
public class AmazonS3Controller {

    private final S3Uploader s3Uploader;

//    @Operation(summary = "s3 이미지 업로드",
//            description = "s3 이미지 업로드",
//            tags = {"amazon-s3-service"})
//    @PostMapping("/upload")
//    public ErrorResponse<List<String>> uploadProductImages(@RequestPart("imageFiles") List<MultipartFile> images) {
//        List<String> imageUrls = s3Uploader.uploadMultiple(images, "products");
//        return new ErrorResponse<>(imageUrls);
//    }
}
