package com.starbucks.starvive.banner.application;

import com.starbucks.starvive.banner.dto.in.AddBannerImageRequestDto;
import com.starbucks.starvive.banner.dto.in.UpdateBannerImageRequestDto;
import com.starbucks.starvive.banner.dto.out.BannerImageResponseDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface BannerImageService {

    UUID uploadBanner(MultipartFile image, AddBannerImageRequestDto addBannerImageRequestDto);
    List<UUID> uploadMultipleBanners(List<MultipartFile> images, AddBannerImageRequestDto addBannerImageRequestDto);
    List<BannerImageResponseDto> getAllBanners();
    void updateBannerImage(UUID bannerId, MultipartFile image, UpdateBannerImageRequestDto updateBannerImageRequestDto);
    void deleteBannerImage(UUID bannerId);
}
