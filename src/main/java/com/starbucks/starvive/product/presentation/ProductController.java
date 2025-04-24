package com.starbucks.starvive.product.presentation;

import com.starbucks.starvive.common.domain.BaseResponseStatus;
import com.starbucks.starvive.common.exception.BaseException;
import com.starbucks.starvive.image.domain.ProductImage;
import com.starbucks.starvive.image.infrastructure.ProductImageRepository;
import com.starbucks.starvive.product.application.ProductService;
import com.starbucks.starvive.product.domain.ProductOption;
import com.starbucks.starvive.product.dto.in.AddProductRequestDto;
import com.starbucks.starvive.product.dto.in.DeleteProductRequestDto;
import com.starbucks.starvive.product.dto.in.UpdateProductRequestDto;
import com.starbucks.starvive.product.dto.out.ProductDetailResponseDto;
import com.starbucks.starvive.product.infrastructure.ProductOptionRepository;
import com.starbucks.starvive.product.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JobLauncher jobLauncher;
    private final Job bestProductJob;
    private final ProductOptionRepository productOptionRepository;
    private final ProductImageRepository productImageRepository;

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.", tags = {"product-service"})
    @PostMapping("/add")
    public void addProduct(@RequestBody AddProductRequestVo addProductRequestVo) {
        productService.addProduct(AddProductRequestDto.from(addProductRequestVo));
    }

    @Operation(summary = "상품 단건 조회", description = "상품 ID로 하나의 상품을 조회합니다.", tags = {"product-service"})
    @GetMapping
    public ProductResponseVo getProduct(@RequestParam("productId") UUID productId) {
        return ProductResponseVo.from(productService.getProduct(productId));
    }

    @Operation(summary = "상품 목록 조회 (무한스크롤)", description = "마지막 상품 ID 기준으로 다음 상품 목록을 조회합니다.", tags = {"product-service"})
    @GetMapping("/all")
    public List<ProductListResponseVo> getAllProducts(
            @RequestParam(required = false) UUID lastProductId,
            @RequestParam(defaultValue = "20") int size) {
        return productService.getProductsByCursor(lastProductId, size).stream()
                .map(ProductListResponseVo::from)
                .toList();
    }

    @Operation(summary = "상품 수정", description = "상품을 수정합니다.", tags = {"product-service"})
    @PutMapping
    public void updateProduct(@RequestBody UpdateProductRequestVo updateProductRequestVo) {
        productService.updateProduct(UpdateProductRequestDto.from(updateProductRequestVo));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.", tags = {"product-service"})
    @DeleteMapping
    public void deleteProduct(@RequestBody DeleteProductRequestVo deleteProductRequestVo) {
        productService.deleteProduct(DeleteProductRequestDto.from(deleteProductRequestVo));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 정보를 조회합니다.", tags = {"product-service"})
    @GetMapping("/detail")
    public ProductDetailResponseVo getProductDetail(@RequestParam("productId") UUID productId) {
        ProductDetailResponseDto dto = productService.getProductDetail(productId);
        return ProductDetailResponseVo.from(dto);
    }

    @Operation(summary = "베스트 상품 목록 조회", description = "인기 상품(베스트 상품) 목록을 순위 순으로 조회합니다.", tags = {"product-service"})
    @GetMapping("/best")
    public List<BestProductResponseVo> getBestProducts() {
        return productService.getBestProducts().stream()
                .map(BestProductResponseVo::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/admin/run-best-product-batch")
    @Operation(summary = "수동 배치 실행 (테스트용)", description = "베스트 상품 배치 작업을 수동으로 즉시 실행합니다.", tags = {"batch-test", "product"})
    public String runBestProductBatchManually() {
        try {
            log.info(">>>> Manual Trigger: bestProductJob 실행 시작...");
            jobLauncher.run(
                    bestProductJob,
                    new JobParametersBuilder()
                            .addLocalDateTime("manualRunDateTime", LocalDateTime.now())
                            .toJobParameters()
            );
            log.info("<<<< Manual Trigger: bestProductJob 실행 완료.");
            return "Best product batch job (Spring Batch) executed successfully!";
        } catch (Exception e) {
            log.error("Manual Trigger: bestProductJob 실행 중 오류 발생", e);
            return "Error executing batch job (Spring Batch): " + e.getMessage();
        }
    }
}