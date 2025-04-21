package com.starbucks.starvive.tag.presentation;

import com.starbucks.starvive.tag.application.ProductTagService;
import com.starbucks.starvive.tag.dto.in.RegisterProductTagRequestDto;
import com.starbucks.starvive.tag.dto.out.ProductTagListResponseDto;
import com.starbucks.starvive.tag.vo.RegisterProductTagVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/product-tag")
@RestController
@RequiredArgsConstructor
public class ProductTagController {

    private final ProductTagService productTagService;

    @Operation(summary = "태그별 상품 등록",
            description = "태그별 상품 등록합니다.",
            tags = {"product-tag-service"})
    @PostMapping
    public void addProductTag(
            @RequestBody RegisterProductTagVo registerProductTagVo
    ) {
        productTagService.addProductTag(RegisterProductTagRequestDto.from(registerProductTagVo));
    }

    @Operation(summary = "태그별 상품 조회",
            description = "태그별 상품 조회합니다.",
            tags = {"product-tag-service"})
    @GetMapping
    public List<ProductTagListResponseDto> getPromotionProduct(
            @RequestParam("tagId") UUID tagId
    ) {
        return productTagService.getProductTagList(tagId);
    }
}
