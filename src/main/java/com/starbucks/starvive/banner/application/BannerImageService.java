package com.starbucks.starvive.banner.application;

import com.starbucks.starvive.banner.dto.in.BannerImageCreateRequestDto;
import com.starbucks.starvive.banner.dto.in.BannerImageUpdateRequestDto;
import com.starbucks.starvive.banner.dto.out.BannerImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface BannerImageService {

    UUID uploadBanner(MultipartFile image, BannerImageCreateRequestDto bannerImageCreateRequestDto);
    List<UUID> uploadMultipleBanners(List<MultipartFile> images, BannerImageCreateRequestDto bannerImageCreateRequestDto);
    void updateBanner(UUID bannerId, MultipartFile image, BannerImageUpdateRequestDto bannerImageUpdateRequestDto);
    void deleteBanner(UUID bannerId);
    List<BannerImageResponseDto> getAllBanners();

}
