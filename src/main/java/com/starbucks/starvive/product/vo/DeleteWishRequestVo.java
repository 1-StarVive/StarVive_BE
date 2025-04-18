package com.starbucks.starvive.product.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteWishRequestVo {

    private UUID productId;
}
