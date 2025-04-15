package com.starbucks.starvive.shippingaddress.vo.in;

import java.util.UUID;
import lombok.Getter;

@Getter
public class UpdateShippingAddressVo {
    private UUID shippingAddressId;
    private String addressNickName;
    private String receiverName;
    private String postalCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;
    private String memo;
    private boolean selectedBase;
}
