package com.starbucks.starvive.shippingaddress.vo.in;

import java.util.UUID;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class DeleteShippipngAddressVo {
    
    private UUID shippingAddressId;
}
