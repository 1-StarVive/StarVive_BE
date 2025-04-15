package com.starbucks.starvive.shippingaddress.application;

import org.springframework.stereotype.Service;
import com.starbucks.starvive.shippingaddress.repository.ShippingAddressRepository;
import com.starbucks.starvive.shippingaddress.dto.in.AddShippingAddressDto;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import java.util.List;
import com.starbucks.starvive.user.domain.User;
import org.springframework.transaction.annotation.Transactional;
import com.starbucks.starvive.shippingaddress.dto.in.UpdateShippingAddressDto;
import com.starbucks.starvive.shippingaddress.dto.in.DeleteShippingAddressRequestDto;

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
    public List<ShippingAddress> getShippingAddress(UserDetails userDetails) {
        User user = (User) userDetails;
        return shippingAddressRepository.findByUserUuidAndDeletedFalse(user.getUserId());
    }

    @Transactional
    @Override
    public void deleteShippingAddress(DeleteShippingAddressRequestDto deleteShippingAddressRequestDto) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(deleteShippingAddressRequestDto.getShippingAddressId())
                .orElseThrow();
        shippingAddress.softDelete();
    }

    @Transactional
    @Override
    public void updateShippingAddress(UUID shippingAddressId, UpdateShippingAddressDto updateShippingAddressDto) {
        ShippingAddress existingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new RuntimeException("수정할 배송지를 찾을 수 없습니다: " + shippingAddressId));

        UUID userId = existingAddress.getUserUuid();

        existingAddress.update(updateShippingAddressDto);

        if (existingAddress.isSelectedBase()) {
            setOnlyOneDefaultAddress(userId, existingAddress.getShippingAddressId());
        }
    }

    @Transactional
    private void setOnlyOneDefaultAddress(UUID userId, UUID defaultAddressId) {
        shippingAddressRepository.updateOtherAddressesSelectedBaseStatus(userId, defaultAddressId, false);
    }
}
