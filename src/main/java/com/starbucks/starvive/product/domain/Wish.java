package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wish {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID wishId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(nullable = false)
    private UUID productOptionId;

    public static Wish create(UUID userId, UUID productId, UUID productOptionId) {
        return Wish.builder()
                .userId(userId)
                .productId(productId)
                .productOptionId(productOptionId)
                .build();
    }
}
