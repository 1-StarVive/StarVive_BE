package com.starbucks.starvive.image.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
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
