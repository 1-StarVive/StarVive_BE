package com.starbucks.starvive.shippingaddress.dto.in;

import java.util.UUID;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import com.starbucks.starvive.shippingaddress.vo.in.UpdateShippingAddressVo;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
@Getter
@Builder
@AllArgsConstructor
public class UpdateShippingAddressDto {

    private String addressNickName;
    private String receiverName;
    private String postalCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;
    private String memo;
    private boolean selectedBase;

    
    
    
    public static UpdateShippingAddressDto from(UpdateShippingAddressVo updateShippingAddressVo) {
        return UpdateShippingAddressDto.builder()
                .addressNickName(updateShippingAddressVo.getAddressNickName())
                .receiverName(updateShippingAddressVo.getReceiverName())
                .postalCode(updateShippingAddressVo.getPostalCode())
                .baseAddress(updateShippingAddressVo.getBaseAddress())
                .detailAddress(updateShippingAddressVo.getDetailAddress())
                .phoneNumber(updateShippingAddressVo.getPhoneNumber())
                .memo(updateShippingAddressVo.getMemo())
                .selectedBase(updateShippingAddressVo.isSelectedBase())
                .build();
    }
}
