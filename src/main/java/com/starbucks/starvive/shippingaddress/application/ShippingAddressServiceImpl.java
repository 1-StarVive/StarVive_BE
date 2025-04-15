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


@RequiredArgsConstructor
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Override
    public void addShippingAddress(AddShippingAddressDto addShippingAddressDto, UserDetails userDetails) {
        User user = (User) userDetails;
        UUID userUuid = user.getUserId();
        shippingAddressRepository.save(addShippingAddressDto.toEntity(userUuid));
    }

    @Override
    public List<ShippingAddress> getShippingAddress(UserDetails userDetails) {
        User user = (User) userDetails;
        return shippingAddressRepository.findByUserUuidAndDeletedFalse(user.getUserId());
    }

    @Transactional
    @Override
    public void deleteShippingAddress(UUID shippingAddressId) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow();
        shippingAddress.softDelete();
    }

    @Transactional
    @Override
    public void updateShippingAddress(UUID shippingAddressId, UpdateShippingAddressDto updateShippingAddressDto) {
        ShippingAddress existingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다: " + shippingAddressId));

        existingAddress.update(updateShippingAddressDto);

        if (existingAddress.isSelectedBase()) {
            UUID userId = existingAddress.getUserUuid();

            List<ShippingAddress> otherAddresses = shippingAddressRepository
                    .findByUserUuidAndDeletedFalseAndShippingAddressIdNot(userId, shippingAddressId);

            boolean needsSave = false;
            if (!otherAddresses.isEmpty()) {
                for (ShippingAddress other : otherAddresses) {
                    if (other.isSelectedBase()) {
                        other.selectedBase = false;
                        needsSave = true;
                    }
                }
                if (needsSave) {
                    shippingAddressRepository.saveAll(otherAddresses);
                }
            }
        }
    }
}
