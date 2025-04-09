package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class VariantOption extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID variantOptionId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productOptionId;

    @Column(nullable = false)
    private String value; // 25m ? M ?

    @Builder
    public VariantOption(UUID variantOptionId, UUID productOptionId, String value) {
        this.variantOptionId = variantOptionId;
        this.productOptionId = productOptionId;
        this.value = value;
    }
}
