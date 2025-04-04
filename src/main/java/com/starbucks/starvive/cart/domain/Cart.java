package com.starbucks.starvive.cart.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID cartId;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId; // 사용자 식별자

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId; // 상품 옵션 식별자 (= productId로 사용)

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity; // 상품 수량

    private Boolean checked; // 체크 여부 (예: 주문 선택)

    private LocalDate deletedAt; // soft delete 필드

    @Builder
    public Cart(UUID cartId, UUID userId, UUID productOptionId,
                UUID productId, Integer quantity,
                Boolean checked, LocalDate deletedAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.productOptionId = productOptionId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
        this.deletedAt = deletedAt;
    }

    // 수량 변경
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 삭제 처리 (soft delete)
    public void softDelete() { this.deletedAt = LocalDate.now(); }}

    //public boolean Deleted() {
        //return this.deletedAt != null;}

