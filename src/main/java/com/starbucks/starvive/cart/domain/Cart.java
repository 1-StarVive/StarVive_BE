package com.starbucks.starvive.cart.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID cartId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId; // 상품 옵션 식별자 (= productId로 사용)

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity;

    private Boolean checked;

    public void increaseQuantity(int value) {
        this.quantity += value;
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    //private LocalDate deletedAt; // soft delete 필드
    private boolean deleted;


         public Cart(UUID userId, UUID productOptionId, int quantity) {
            this.userId = userId;
            this.productOptionId = productOptionId;
            this.quantity = quantity;
        }
    }
