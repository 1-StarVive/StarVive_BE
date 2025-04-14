package com.starbucks.starvive.product.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteProductRequestVo {

    private UUID productId;

}