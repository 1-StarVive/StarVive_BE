package com.starbucks.starvive.category.dto.in;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TopCategoryForm {

    private final String name;
    private final String thumbAlt;
    private final MultipartFile image;

    @Builder
    public TopCategoryForm(String name, String thumbAlt, MultipartFile image) {
        this.name = name;
        this.thumbAlt = thumbAlt;
        this.image = image;
    }
}
