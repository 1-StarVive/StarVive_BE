package com.starbucks.starvive.shippingaddress.dto.in;

import jakarta.validation.constraints.NotNull; // 유효성 검사 추가
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.starbucks.starvive.shippingaddress.vo.in.DeleteShippipngAddressVo;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class DeleteShippingAddressRequestDto {

    @NotNull()
    private UUID shippingAddressId;

    public static DeleteShippingAddressRequestDto from(DeleteShippipngAddressVo deleteShippipngAddressVo) {
        return DeleteShippingAddressRequestDto.builder()
                .shippingAddressId(deleteShippipngAddressVo.getShippingAddressId())
                .build();
    }
} 