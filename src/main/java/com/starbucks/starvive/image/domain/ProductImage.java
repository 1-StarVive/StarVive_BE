package com.starbucks.starvive.image.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productThumbId;

    private String imageUrl;

    private String alt;

    private Boolean isMainImage;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;
}
