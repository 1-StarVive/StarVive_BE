package com.starbucks.starvive.tag.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RegisterProductTagVo {

    private UUID tagId;

    private List<UUID> productIds;

    @Builder
    public RegisterProductTagVo(UUID tagId, List<UUID> productIds) {
        this.tagId = tagId;
        this.productIds = productIds;
    }
}
