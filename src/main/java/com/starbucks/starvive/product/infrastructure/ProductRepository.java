package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


    // 선택: 베스트 상품만 조회
    List<Product> findByHasBestTrue();

    List<Product> findAllByIds(List<UUID> productIds);
    List<Product> findAllById(List<? extends UUID> ids);

    // 선택: 특정 productId 목록 중 베스트 여부 포함하여 조회



//    @Query("SELECT new com.starbucks.starvive.product.vo.ProductListVO(" +
//            "p.productId, p.name, pi.imageUrl, pi.alt, po.price, p.discountRate, " +
//            "p.isNew, p.isTop, p.isLimitedEdition) " +
//            "FROM Product p " +
//            "LEFT JOIN ProductImage pi ON p.productId = pi.productId AND pi.isMainImage = true " +
//            "LEFT JOIN ProductOption po ON p.productId = po.productId")
//    List<ProductListVO> findAllProducts();
}
