package com.starbucks.starvive.product.infrastructure;

import com.starbucks.starvive.product.domain.Product;
import com.starbucks.starvive.product.vo.ProductListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

//    @Query("SELECT new com.starbucks.starvive.product.vo.ProductListVO(" +
//            "p.productId, p.name, pi.imageUrl, pi.alt, po.price, p.discountRate, " +
//            "p.isNew, p.isTop, p.isLimitedEdition) " +
//            "FROM Product p " +
//            "LEFT JOIN ProductImage pi ON p.productId = pi.productId AND pi.isMainImage = true " +
//            "LEFT JOIN ProductOption po ON p.productId = po.productId")
//    List<ProductListVO> findAllProducts();
}
