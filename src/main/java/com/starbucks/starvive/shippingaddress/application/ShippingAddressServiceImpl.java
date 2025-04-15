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
    @Transactional
    public void addShippingAddress(AddShippingAddressDto addShippingAddressDto, UserDetails userDetails) {
        User user = (User) userDetails;
        UUID userId = user.getUserId();
        ShippingAddress addressToAdd = addShippingAddressDto.toEntity(userId);

        // Save the new address first
        ShippingAddress savedAddress = shippingAddressRepository.save(addressToAdd);

        // If the new address is set as default, handle others
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
    public void deleteShippingAddress(UUID shippingAddressId) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new RuntimeException("삭제할 배송지를 찾을 수 없습니다: " + shippingAddressId));
        shippingAddress.softDelete();
    }

    @Transactional
    @Override
    public void updateShippingAddress(UUID shippingAddressId, UpdateShippingAddressDto updateShippingAddressDto) {
        ShippingAddress existingAddress = shippingAddressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new RuntimeException("수정할 배송지를 찾을 수 없습니다: " + shippingAddressId));

        UUID userId = existingAddress.getUserUuid();

        // Update the address using the entity's method
        existingAddress.update(updateShippingAddressDto);

        // If this address is now the default, handle others
        if (existingAddress.isSelectedBase()) {
            setOnlyOneDefaultAddress(userId, existingAddress.getShippingAddressId());
        }
    }

    /**
     * Helper method to ensure only one address is marked as default for a user.
     * Sets the specified address as default and unsets others.
     * NOTE: This method assumes the target default address itself is already marked as true.
     * @param userId The ID of the user.
     * @param defaultAddressId The ID of the address to be set as default.
     */
    @Transactional
    private void setOnlyOneDefaultAddress(UUID userId, UUID defaultAddressId) {
        // Find all other non-deleted addresses for the user
        List<ShippingAddress> otherAddresses = shippingAddressRepository
                .findByUserUuidAndDeletedFalseAndShippingAddressIdNot(userId, defaultAddressId);

        if (!otherAddresses.isEmpty()) {
            for (ShippingAddress other : otherAddresses) {
                // If another address is marked as default, unset it using the setter
                if (other.isSelectedBase()) {
                    other.setSelectedBase(false);
                }
            }
        }
    }

    // // Method to set a specific address as default (called by controller)
    // @Transactional
    // @Override
    // public void setDefaultShippingAddress(UUID shippingAddressId, UserDetails userDetails) {
    //     User user = (User) userDetails;
    //     UUID userId = user.getUserId();

    //     ShippingAddress targetAddress = shippingAddressRepository.findById(shippingAddressId)
    //         .orElseThrow(() -> new RuntimeException("기본으로 설정할 배송지를 찾을 수 없습니다: " + shippingAddressId));

    //     // Check if the address belongs to the user making the request (optional but recommended)
    //     if (!targetAddress.getUserUuid().equals(userId)) {
    //         throw new SecurityException("자신의 배송지만 기본으로 설정할 수 있습니다.");
    //     }

    //     // Check if it's already the default
    //     if (targetAddress.isSelectedBase()) {
    //         return; // Already default, nothing to do
    //     }

    //     // Set the target address as default
    //     targetAddress.setSelectedBase(true);

    //     // Unset other addresses as default
    //     setOnlyOneDefaultAddress(userId, shippingAddressId);
    // }
}
