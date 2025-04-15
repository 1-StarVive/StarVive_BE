package com.starbucks.starvive.shippingaddress.dto.in;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import com.starbucks.starvive.shippingaddress.vo.in.AddShippingAddressVo;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class AddShippingAddressDto {

    private String addressNickName;
    private String receiverName;
    private String postalCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;
    private String memo;
    private boolean selectedBase;

    public ShippingAddress toEntity(UUID userUuid) {
        return ShippingAddress.builder()
                .addressNickName(this.addressNickName)
                .receiverName(this.receiverName)
                .postalCode(this.postalCode)
                .baseAddress(this.baseAddress)
                .detailAddress(this.detailAddress)
                .phoneNumber(this.phoneNumber)
                .memo(this.memo)
                .selectedBase(this.selectedBase)
                .userUuid(userUuid)
                .build();
    }
    
    public static AddShippingAddressDto from(AddShippingAddressVo addShippingAddressVo) {
        return AddShippingAddressDto.builder()
                .addressNickName(addShippingAddressVo.getAddressNickName())
                .receiverName(addShippingAddressVo.getReceiverName())
                .postalCode(addShippingAddressVo.getPostalCode())
                .baseAddress(addShippingAddressVo.getBaseAddress())
                .detailAddress(addShippingAddressVo.getDetailAddress())
                .phoneNumber(addShippingAddressVo.getPhoneNumber())
                .memo(addShippingAddressVo.getMemo())
                .selectedBase(addShippingAddressVo.isSelectedBase())
                .build();
    }

}
