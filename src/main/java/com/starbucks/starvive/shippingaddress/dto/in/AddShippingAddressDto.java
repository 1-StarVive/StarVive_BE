package com.starbucks.starvive.shippingaddress.dto.in;

import lombok.Getter;

@Getter
public class AddShippingAddressDto {
    private String addressNickName;
    private String receiverName;
    private String postalCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;
    private String memo;
    private boolean selectedBase;
}
