package com.starbucks.starvive.shippingaddress.dto.in;

import com.starbucks.starvive.shippingaddress.vo.in.UpdateShippingAddressVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Builder
    public UpdateShippingAddressDto(String addressNickName,
     String receiverName, String postalCode, String baseAddress,
      String detailAddress, String phoneNumber, String memo, boolean selectedBase) {
        this.addressNickName = addressNickName;
        this.receiverName = receiverName;
        this.postalCode = postalCode;
        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
        this.memo = memo;
        this.selectedBase = selectedBase;
    }
}
