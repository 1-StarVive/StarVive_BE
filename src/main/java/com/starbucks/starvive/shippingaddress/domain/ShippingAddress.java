package com.starbucks.starvive.shippingaddress.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import com.starbucks.starvive.shippingaddress.dto.in.UpdateShippingAddressDto;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingAddress {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    UUID shippingAddressId;

    @Column(nullable = true, length = 100)
    String addressNickName;

    @Column(nullable = true, length = 100)
    String receiverName;

    @Column(nullable = false, length = 10)
    String postalCode;

    @Column(nullable = false, length = 50)
    String baseAddress;

    @Column(nullable = false, length = 50)
    String detailAddress;

    @Column(nullable = false, length = 20)
    String phoneNumber;

    @Column(nullable = true, length = 50)
    String memo;

    @Column(nullable = false)
    boolean selectedBase;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    UUID userUuid;

    @Column(nullable = false)
    private boolean deleted = false;

    @Builder
    private ShippingAddress(UUID shippingAddressId, String addressNickName, String receiverName, String postalCode, String baseAddress, String detailAddress, String phoneNumber, String memo, boolean selectedBase, UUID userUuid, boolean deleted) {
        this.shippingAddressId = shippingAddressId;
        this.addressNickName = addressNickName;
        this.receiverName = receiverName;
        this.postalCode = postalCode;
        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
        this.memo = memo;
        this.selectedBase = selectedBase;
        this.userUuid = userUuid;
        this.deleted = deleted;
    }
    

    public void update(UpdateShippingAddressDto updateShippingAddressDto) {
        this.addressNickName = updateShippingAddressDto.getAddressNickName();
        this.receiverName = updateShippingAddressDto.getReceiverName();
        this.postalCode = updateShippingAddressDto.getPostalCode();
        this.baseAddress = updateShippingAddressDto.getBaseAddress();
        this.detailAddress = updateShippingAddressDto.getDetailAddress();
        this.phoneNumber = updateShippingAddressDto.getPhoneNumber();
        this.memo = updateShippingAddressDto.getMemo();
        this.selectedBase = updateShippingAddressDto.isSelectedBase();
    }
    

    public void softDelete() {
        this.deleted = true;
    }

}
