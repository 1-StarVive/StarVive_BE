package com.starbucks.starvive.shippingaddress.dto.in;

import jakarta.validation.constraints.NotNull; 
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.starbucks.starvive.shippingaddress.vo.in.DeleteShippipngAddressVo;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeleteShippingAddressRequestDto {

    @NotNull()
    private UUID shippingAddressId;

    public static DeleteShippingAddressRequestDto from(DeleteShippipngAddressVo deleteShippipngAddressVo) {
        return DeleteShippingAddressRequestDto.builder()
                .shippingAddressId(deleteShippipngAddressVo.getShippingAddressId())
                .build();
    }

    @Builder
    public DeleteShippingAddressRequestDto(UUID shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }
} 