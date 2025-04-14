package com.starbucks.starvive.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.starbucks.starvive.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile multipartFile, String folderName) {
        String originalFilename = multipartFile.getOriginalFilename();

        if (originalFilename == null) {
            throw new BaseException(S3_EMPTY_FILE_NAME);
        }

        String ext = getExtension(originalFilename);
        String fileName = folderName + "/" + UUID.randomUUID() + ext;

        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), null);
        } catch (IOException e) {
            throw new BaseException(S3_UPLOAD_FAILED);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public List<String> uploadMultiple(List<MultipartFile> multipartFiles, String folderName) {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : multipartFiles) {
            String uploadUrl = upload(file, folderName);
            uploadedUrls.add(uploadUrl);
        }
        return uploadedUrls;
    }

    public String updateFile(MultipartFile file, String key) {
        try {
            amazonS3.putObject(bucket, key, file.getInputStream(), null);
            return amazonS3.getUrl(bucket, key).toString();
        } catch (IOException e) {
            throw new BaseException(S3_UPLOAD_FAILED);
        }
    }

    public void deleteFile(String fileName) {
        try {
            amazonS3.deleteObject(bucket, fileName);
        } catch (Exception e) {
            throw new BaseException(S3_DELETE_FAILED);
        }
    }

    private String getExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new BaseException( S3_INVALID_FILE_FORMAT);
        }
    }
}