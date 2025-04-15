package com.starbucks.starvive.cart.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteSelectedCartItemsRequestVo {

    private UUID userId;
    private List<UUID> cartItemIds;

}
