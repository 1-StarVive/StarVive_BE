package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.TopCategoryService;
import com.starbucks.starvive.category.dto.in.DeleteTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateTopCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.TopCategoryResponseDto;
import com.starbucks.starvive.category.vo.DeleteTopCategoryRequestVo;
import com.starbucks.starvive.category.vo.RegisterTopCategoryVo;
import com.starbucks.starvive.category.vo.UpdateTopCategoryRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/top-categories")
@RestController
@RequiredArgsConstructor
public class TopCategoryController {

    private final TopCategoryService topCategoryService;

    @Operation(summary = "상위 카테고리 등록", description = "상위 카테고리를 등록합니다.",
            tags = {"top-category-service"})
    @PostMapping(value = "/add")
        public void addTopCategory(

            // Controller 단에서는 vo로 받기 (res, req 구분해서 알아서 잘 ..)
            // TopCategoryRequestVo -> TopCategoryCreateRequestVo 클래스명 쪼개서 나누기
            @RequestPart("request") RegisterTopCategoryVo registerTopCategoryVo,
            @RequestPart("files") MultipartFile multipartFile
            ) {

        topCategoryService.addTopCategory(registerTopCategoryVo, multipartFile);
    }


    @Operation(summary = "상위 카테고리 조회", description = "상위 카테고리를 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping("/all")
    public List<TopCategoryResponseDto> getTopCategories() {
        return topCategoryService.findTopCategories();
    }

    @Operation(summary = "상위 카테고리 ID 하나만 조회",
            description = "상위 카테고리 ID를 통해 하나만 조회합니다.",
            tags = {"top-category-service"})
    @GetMapping
    public TopCategoryResponseDto getTopCategory(
            // 주소 처럼 받지 말고 @RequestParam 이런 형태로 받기 -> /api/v1/top-categories?topId=topCategoryIdUUID)
            @RequestParam("topId") UUID topCategoryId) {
        return topCategoryService.findTopCategoriesId(topCategoryId);
    }

    @Operation(summary = "상위 카테고리 수정", description = "상위 카테고리를 수정합니다.",
            tags = {"top-category-service"})
    @PutMapping
    public void updateTopCategory(
            // TopCategoryRequestVo -> UpdateTopCategoryRequestVo 클래스명 쪼개서 나누기
            @RequestBody UpdateTopCategoryRequestVo updateTopCategoryRequestVo
    ) {
        topCategoryService.updateTopCategory(UpdateTopCategoryRequestDto.from(updateTopCategoryRequestVo));
    }

    @Operation(summary = "상위 카테고리 삭제", description = "상위 카테고리를 삭제합니다.",
            tags = {"top-category-service"})
    @DeleteMapping
    public void deleteTopCategory(
            // TopCategoryRequestVo -> DeleteTopCategoryRequestVo 클래스명 쪼개서 나누기
            @RequestBody DeleteTopCategoryRequestVo deleteTopCategoryRequestVo
    ) {
        topCategoryService.deleteTopCategory(DeleteTopCategoryRequestDto.from(deleteTopCategoryRequestVo));
    }
}