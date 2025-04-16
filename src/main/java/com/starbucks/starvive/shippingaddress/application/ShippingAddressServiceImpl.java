package com.starbucks.starvive.shippingaddress.application;

import org.springframework.stereotype.Service;

import com.starbucks.starvive.shippingaddress.dto.in.AddShippingAddressDto;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import java.util.List;
import com.starbucks.starvive.user.domain.User;
import org.springframework.transaction.annotation.Transactional;
import com.starbucks.starvive.shippingaddress.dto.in.UpdateShippingAddressDto;
import com.starbucks.starvive.shippingaddress.infrastructure.ShippingAddressRepository;
import com.starbucks.starvive.shippingaddress.dto.in.DeleteShippingAddressRequestDto;
import com.starbucks.starvive.common.domain.BaseResponseStatus;
import com.starbucks.starvive.common.exception.BaseException;

@RequiredArgsConstructor
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Override
    @Transactional
    public void addShippingAddress(AddShippingAddressDto addShippingAddressDto, UserDetails userDetails) {
        User user = (User) userDetails;
        UUID userId = user.getUserId();
        
        ShippingAddress savedAddress = shippingAddressRepository.save(addShippingAddressDto.toEntity(userId));
        
        if (savedAddress.isSelectedBase()) {
            setOnlyOneDefaultAddress(userId, savedAddress.getShippingAddressId());
        }
    }

    @Override
    @Transactional
    public List<ShippingAddress> getShippingAddress(UserDetails userDetails) {
        User user = (User) userDetails;
        return shippingAddressRepository.findByUserUuidAndDeletedFalse(user.getUserId());
    }

    @Override
    @Transactional
    public void deleteShippingAddress(DeleteShippingAddressRequestDto deleteShippingAddressRequestDto) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(deleteShippingAddressRequestDto.getShippingAddressId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_SHIPPING_ADDRESS));
        shippingAddress.softDelete();
    }

    @Override
    @Transactional
    public void updateShippingAddress(UUID shippingAddressId, UpdateShippingAddressDto updateShippingAddressDto) {
        ShippingAddress existingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_SHIPPING_ADDRESS));

        UUID userId = existingAddress.getUserUuid();

        existingAddress.update(updateShippingAddressDto);

        if (existingAddress.isSelectedBase()) {
            setOnlyOneDefaultAddress(userId, existingAddress.getShippingAddressId());
        }
    }

    private void setOnlyOneDefaultAddress(UUID userId, UUID defaultAddressId) {
        shippingAddressRepository.updateOtherAddressesSelectedBaseStatus(userId, defaultAddressId, false);
    }
}
