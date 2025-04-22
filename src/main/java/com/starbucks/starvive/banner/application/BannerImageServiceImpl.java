package com.starbucks.starvive.banner.application;

import com.starbucks.starvive.banner.domain.Banner;
import com.starbucks.starvive.banner.dto.in.AddBannerImageRequestDto;
import com.starbucks.starvive.banner.dto.in.UpdateBannerImageRequestDto;
import com.starbucks.starvive.banner.dto.out.BannerImageResponseDto;
import com.starbucks.starvive.banner.infrastructure.BannerImageRepository;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.common.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerImageServiceImpl implements BannerImageService {

    private final BannerImageRepository bannerImageRepository;
    private final S3Uploader s3Uploader;
    private static final String FOLDER_NAME = "banner";

    @Override
    public UUID uploadBanner(MultipartFile image, AddBannerImageRequestDto addBannerImageRequestDto) {
        if (image == null || image.isEmpty()) {
            throw new BaseException(S3_EMPTY_FILE_NAME);
        }

        String imageUrl = s3Uploader.upload(image, FOLDER_NAME);
        Banner banner = buildBanner(imageUrl, addBannerImageRequestDto);

        return bannerImageRepository.save(banner).getBannerId();
    }

    @Override
    public List<UUID> uploadMultipleBanners(List<MultipartFile> images, AddBannerImageRequestDto addBannerImageRequestDto) {
        if (images == null || images.isEmpty()) {
            throw new BaseException(S3_EMPTY_FILE_LIST);
        }

        List<String> imageUrls = s3Uploader.uploadMultiple(images, FOLDER_NAME);
        List<UUID> savedIds = new ArrayList<>();

        for (String imageUrl : imageUrls) {
            Banner banner = buildBanner(imageUrl, addBannerImageRequestDto);
            savedIds.add(bannerImageRepository.save(banner).getBannerId());
        }

        return savedIds;
    }

    @Override
    public List<BannerImageResponseDto> getAllBanners() {
        return bannerImageRepository.findAll().stream()
                .filter(Banner::getActivated) // Soft delete 제외
                .map(BannerImageResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBannerImage(UUID bannerId, MultipartFile image, UpdateBannerImageRequestDto updateBannerImageRequestDto) {
        Banner banner = bannerImageRepository.findById(bannerId)
                .orElseThrow(() -> new BaseException(NO_EXIST_BANNER));

        if (image != null && !image.isEmpty()) {

            log.info("Updating banner image for bannerId: {}", bannerId);
            String updatedUrl = s3Uploader.upload(image, FOLDER_NAME);
            banner.updateImage(updatedUrl);
        }

        banner.updateInfo(updateBannerImageRequestDto);
    }

    @Override
    @Transactional
    public void deleteBannerImage(UUID bannerId) {
        Banner banner = bannerImageRepository.findById(bannerId)
                .orElseThrow(() -> new BaseException(NO_EXIST_BANNER));

        if (banner.getImageBannerUrl() != null) {
            s3Uploader.deleteFile(banner.getImageBannerUrl());
        }

        banner.softDelete();
    }

    // 공통 Banner 빌더
    private Banner buildBanner(String imageUrl, AddBannerImageRequestDto addBannerImageRequestDto) {
        return Banner.builder()
                .imageBannerUrl(imageUrl)
                .imageBannerAlt(addBannerImageRequestDto.getImageBannerAlt())
                .postedAt(addBannerImageRequestDto.getPostedAt())
                .activated(addBannerImageRequestDto.isActivated())
                .build();
    }
}

