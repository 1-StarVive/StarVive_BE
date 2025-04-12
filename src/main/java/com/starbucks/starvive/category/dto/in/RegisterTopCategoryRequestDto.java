package com.starbucks.starvive.category.dto.in;

import com.starbucks.starvive.category.domain.TopCategory;
import com.starbucks.starvive.category.dto.out.RegisterTopCategoryResponseDto;
import com.starbucks.starvive.category.vo.RegisterTopCategoryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterTopCategoryRequestDto {

    private String name;

    private String imageUrl;

    private String imageAlt;

    @Builder
    public RegisterTopCategoryRequestDto(String name,
                                         String imageUrl, String imageAlt) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
    }

    public TopCategory toTopCategory() {
        return TopCategory.builder()
                .name(name)
                .imageUrl(imageUrl)
                .imageAlt(imageAlt)
                .build();
    }

    public static RegisterTopCategoryRequestDto of(RegisterTopCategoryRequestVo registerTopCategoryRequestVo, String imageUrl) {
        return RegisterTopCategoryRequestDto.builder()
                .name(registerTopCategoryRequestVo.getName())
                .imageUrl(imageUrl)
                .imageAlt(registerTopCategoryRequestVo.getImageAlt())
                .build();
    }
}
