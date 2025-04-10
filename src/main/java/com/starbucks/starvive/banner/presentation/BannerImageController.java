package com.starbucks.starvive.banner.presentation;

import com.starbucks.starvive.banner.application.BannerImageService;
import com.starbucks.starvive.banner.dto.in.BannerImageCreateRequestDto;
import com.starbucks.starvive.banner.dto.in.BannerImageUpdateRequestDto;
import com.starbucks.starvive.banner.dto.out.BannerImageResponseDto;
import com.starbucks.starvive.common.domain.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banners")
public class BannerImageController {

    private final BannerImageService bannerImageService;
    /**
     * ✅ 1. 단일 이미지 등록
     */
    @PostMapping("/single")
    public BaseResponseEntity<UUID> uploadBanner(@RequestPart MultipartFile image,
                                                 @RequestPart BannerImageCreateRequestDto BannerImageCreateRequestDto) {
        UUID bannerId = bannerImageService.uploadBanner(image, BannerImageCreateRequestDto);
        return BaseResponseEntity.ok(bannerId);
    }

    /**
     * ✅ 2. 다중 이미지 등록
     */
    @PostMapping("/multi")
    public BaseResponseEntity<List<UUID>> uploadMultipleBanners(@RequestPart List<MultipartFile> images,
                                                                @RequestPart BannerImageCreateRequestDto BannerImageCreateRequestDto) {
        return BaseResponseEntity.ok(bannerImageService.uploadMultipleBanners(images, BannerImageCreateRequestDto));
    }

    /**
     * ✅ 3. 전체 배너 조회
     */
    @GetMapping
    public BaseResponseEntity<List<BannerImageResponseDto>> getAllBanners() {
        return BaseResponseEntity.ok(bannerImageService.getAllBanners());
    }

    /**
     * ✅ 4. 배너 수정 (이미지 교체 가능)
     */
    @PutMapping("/{bannerId}")
    public BaseResponseEntity<Void> update(@PathVariable UUID bannerId,
                                           @RequestPart(required = false) MultipartFile image,
                                           @RequestPart BannerImageUpdateRequestDto bannerImageUpdateRequestDto) {
        bannerImageService.updateBanner(bannerId, image, bannerImageUpdateRequestDto);
        return BaseResponseEntity.ok();
    }

    /**
     * ✅ 5. 배너 삭제
     */
    @DeleteMapping("/{bannerId}")
    public BaseResponseEntity<Void> delete(@PathVariable UUID bannerId) {
        bannerImageService.deleteBanner(bannerId);
        return BaseResponseEntity.ok();
    }

}