package com.starbucks.starvive.cart.domain;

import com.starbucks.starvive.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
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

    @Column(nullable = false)
    private Boolean checked;


    @Builder
    public Cart(UUID cartId, UUID userId, UUID productId, UUID productOptionId, Integer quantity, Boolean checked) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public void update(UUID productOptionId, int quantity, boolean checked) {
        this.productOptionId = productOptionId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public void updateChecked(boolean checked) {
        this.checked = checked;
    }
}