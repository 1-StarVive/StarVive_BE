package com.starbucks.starvive.cart.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Cart extends BaseEntity {

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId; // 사용자 식별자

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID productOptionId; // 상품 옵션 식별자 (= productId로 사용)

    @Column(nullable = false)
    private Integer quantity; // 상품 수량

    @Temporal(TemporalType.TIMESTAMP)
    private Date date; // 생성일자 (담은 시각)

    private Boolean checked; // 체크 여부 (예: 주문 선택)

    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt; // soft delete용 필드

    // 수량 변경
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 삭제 처리 (soft delete)
    public void softDelete() {
        this.deletedAt = new Date();
    }

    //public boolean Deleted() {
        //return this.deletedAt != null;}

}