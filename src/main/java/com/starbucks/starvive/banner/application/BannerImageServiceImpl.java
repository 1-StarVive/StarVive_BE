package com.starbucks.starvive.banner.application;

import com.starbucks.starvive.banner.domain.Banner;
import com.starbucks.starvive.banner.dto.in.BannerImageCreateRequestDto;
import com.starbucks.starvive.banner.dto.in.BannerImageUpdateRequestDto;
import com.starbucks.starvive.banner.dto.out.BannerImageResponseDto;
import com.starbucks.starvive.banner.infrastructure.BannerImageRepository;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.common.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.BANNER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerImageServiceImpl implements BannerImageService {

    private final BannerImageRepository bannerImageRepository;
    private final S3Uploader s3Uploader;

    @Override
    public UUID uploadBanner(MultipartFile image, BannerImageCreateRequestDto dto) {
        String imageUrl = s3Uploader.upload(image, "banner");
        Banner banner = bannerImageRepository.save(dto.toEntity(imageUrl));
        return banner.getBannerId();
    }

    @Override
    public List<UUID> uploadMultipleBanners(List<MultipartFile> images, BannerImageCreateRequestDto dto) {
        List<String> imageUrls = s3Uploader.uploadMultiple(images, "banner");

        return imageUrls.stream()
                .map(url -> {
                    Banner saved = bannerImageRepository.save(dto.toEntity(url));
                    return saved.getBannerId();
                })
                .toList();
    }

    @Override
    public List<BannerImageResponseDto> getAllBanners() {
        return bannerImageRepository.findAll().stream()
                .map(BannerImageResponseDto::from)
                .toList();
    }

    @Override
    public void updateBanner(UUID bannerId, MultipartFile image, BannerImageUpdateRequestDto dto) {
        Banner banner = bannerImageRepository.findById(bannerId)
                .orElseThrow(() -> new BaseException(BANNER_NOT_FOUND));

        String imageUrl = image != null ? s3Uploader.upload(image, "banner") : banner.getImageBannerUrl();

        banner.update(imageUrl, dto.getImageBannerAlt(), dto.getLinkUrl(), dto.getPostedAt(), dto.getActivated());
    }

    @Override
    public void deleteBanner(UUID bannerId) {
        Banner banner = bannerImageRepository.findById(bannerId)
                .orElseThrow(() -> new BaseException(BANNER_NOT_FOUND));
        s3Uploader.deleteFile(banner.getImageBannerUrl().substring(banner.getImageBannerUrl().lastIndexOf("/") + 1));
        bannerImageRepository.delete(banner);
    }
}
