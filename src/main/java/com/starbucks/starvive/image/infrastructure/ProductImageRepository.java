package com.starbucks.starvive.image.infrastructure;

import com.starbucks.starvive.image.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    Optional<ProductImage> findByProductIdAndProductImageId(UUID productId, UUID productImageId);

    Collection<Object> findThumbnailsByProductId(List<UUID> productIds);
    //  List<ProductImage> findAllByProductId(UUID productId);


}

