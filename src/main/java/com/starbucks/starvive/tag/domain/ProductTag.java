package com.starbucks.starvive.tag.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTag extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productTagId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID tagId;

    @Builder
    public ProductTag(UUID productTagId, UUID productId, UUID tagId) {
        this.productTagId = productTagId;
        this.productId = productId;
        this.tagId = tagId;
    }
}
