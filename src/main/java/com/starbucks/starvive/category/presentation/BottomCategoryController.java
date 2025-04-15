package com.starbucks.starvive.category.presentation;

import com.starbucks.starvive.category.application.BottomCategoryService;
import com.starbucks.starvive.category.dto.in.BottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.DeleteBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.in.UpdateBottomCategoryRequestDto;
import com.starbucks.starvive.category.dto.out.BottomCategoryResponseDto;
import com.starbucks.starvive.category.vo.BottomCategoryRequestVo;
import com.starbucks.starvive.category.vo.DeleteBottomCategoryRequestVo;
import com.starbucks.starvive.category.vo.UpdateBottomCategoryRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/bottom-categories")
@RestController
@RequiredArgsConstructor
public class BottomCategoryController {

    private final BottomCategoryService bottomCategoryService;

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 등록",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 등록합니다.",
            tags = {"bottom-category-service"})
    @PostMapping("/add")
    public void addBottomCategory(
            @RequestBody BottomCategoryRequestVo bottomCategoryRequestVo
            ) {
        bottomCategoryService.addBottomCategory(BottomCategoryRequestDto.from(bottomCategoryRequestVo));
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 전체 조회",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 전체 조회합니다.",
            tags = {"bottom-category-service"})
    @GetMapping("/all")
    public List<BottomCategoryResponseDto> getAllBottomCategories() {
        return bottomCategoryService.findBottomCategories();
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 조회",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리(필터)를 조회합니다.",
            tags = {"bottom-category-service"})
    @GetMapping
    public List<BottomCategoryResponseDto> getBottomCategories(@RequestParam("middleId") UUID middleCategoryId) {
        return bottomCategoryService.findBottomCategories(middleCategoryId);
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 수정",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 수정합니다.",
            tags = {"bottom-category-service"})
    @PutMapping
    public void updateBottomCategory(
            @RequestBody UpdateBottomCategoryRequestVo updateBottomCategoryRequestVo
            ) {
        bottomCategoryService.updateBottomCategory(UpdateBottomCategoryRequestDto.from(updateBottomCategoryRequestVo));
    }

    @Operation(summary = "중간 카테고리 기준 하위 카테고리 삭제",
            description = "중간 카테고리 ID를 기반으로 하위 카테고리를 삭제합니다.",
            tags = {"bottom-category-service"})
    @DeleteMapping
    public void deleteBottomCategory(
            @RequestBody DeleteBottomCategoryRequestVo deleteBottomCategoryRequestVo
    ) {
       bottomCategoryService.deleteBottomCategory(DeleteBottomCategoryRequestDto.from(deleteBottomCategoryRequestVo));
    }
}
