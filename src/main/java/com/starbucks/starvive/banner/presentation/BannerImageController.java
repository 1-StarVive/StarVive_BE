package com.starbucks.starvive.banner.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbucks.starvive.banner.application.BannerImageService;
import com.starbucks.starvive.banner.dto.in.AddBannerImageRequestDto;
import com.starbucks.starvive.banner.dto.in.UpdateBannerImageRequestDto;
import com.starbucks.starvive.banner.dto.out.BannerImageResponseDto;
import com.starbucks.starvive.banner.vo.AddBannerImageRequestVo;
import com.starbucks.starvive.banner.vo.UpdateBannerImageRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/banner")
@RequiredArgsConstructor
public class BannerImageController {

    private final BannerImageService bannerImageService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "단일 배너 등록", description = "단일 배너 이미지를 등록합니다.", tags = {"banner-image"})
    @PostMapping("/single")
    public UUID uploadBanner(
            @RequestPart("image") MultipartFile image,
            @RequestPart("addBannerImageRequestVo") String json
    ) throws JsonProcessingException {

        // JSON 문자열 → VO 객체 수동 변환
        AddBannerImageRequestVo addBannerImageRequestVo = objectMapper.readValue(json, AddBannerImageRequestVo.class);

        return bannerImageService.uploadBanner(image, AddBannerImageRequestDto.from(addBannerImageRequestVo));
    }

    @Operation(summary = "다중 배너 등록", description = "배너 이미지를 여러 개 등록합니다.", tags = {"banner-image"})
    @PostMapping("/multi")
    public List<UUID> uploadMultipleBanners(
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("addBannerImageRequestVo") String json
    ) throws JsonProcessingException {

        AddBannerImageRequestVo addBannerImageRequestVo = objectMapper.readValue(json, AddBannerImageRequestVo.class);

        return bannerImageService.uploadMultipleBanners(images, AddBannerImageRequestDto.from(addBannerImageRequestVo));
    }

    @Operation(summary = "배너 전체 조회", description = "등록된 모든 배너 이미지를 조회합니다.", tags = {"banner-image"})
    @GetMapping("/all")
    public List<BannerImageResponseDto> getAllBanners() {
        return bannerImageService.getAllBanners();
    }

    @Operation(summary = "배너 수정", description = "배너 정보를 수정합니다.", tags = {"banner-image"})
    @PutMapping
    public void updateBanner(@RequestPart(required = false) MultipartFile image,
                             @RequestPart UpdateBannerImageRequestVo updateBannerImageRequestVo) {
        bannerImageService.updateBannerImage(
                updateBannerImageRequestVo.getBannerId(),
                image,
                UpdateBannerImageRequestDto.from(updateBannerImageRequestVo));
    }

    @Operation(summary = "배너 삭제", description = "배너 이미지를 삭제합니다.", tags = {"banner-image"})
    @DeleteMapping
    public void deleteBanner(@RequestParam("bannerId") UUID bannerId) {
        bannerImageService.deleteBannerImage(bannerId);
    }
}
