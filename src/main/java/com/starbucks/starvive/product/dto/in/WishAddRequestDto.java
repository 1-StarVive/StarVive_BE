package com.starbucks.starvive.product.dto.in;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
public class WishAddRequestDto {


    private String userId;

    private String productId;
}
