package com.starbucks.starvive.product.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ColorOption extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID colorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String colorCode;

    @Builder
    public ColorOption(UUID colorId, String name, String colorCode) {
        this.colorId = colorId;
        this.name = name;
        this.colorCode = colorCode;
    }
}
