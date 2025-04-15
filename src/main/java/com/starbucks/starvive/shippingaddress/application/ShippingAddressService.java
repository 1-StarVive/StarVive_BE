package com.starbucks.starvive.shippingaddress.application;

import com.starbucks.starvive.shippingaddress.dto.in.AddShippingAddressDto;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import java.util.UUID;
import com.starbucks.starvive.shippingaddress.dto.in.UpdateShippingAddressDto;
import com.starbucks.starvive.shippingaddress.dto.in.DeleteShippingAddressRequestDto;
public interface ShippingAddressService {

    void addShippingAddress(AddShippingAddressDto addShippingAddressDto, UserDetails userDetails);

    List<ShippingAddress> getShippingAddress(UserDetails userDetails);

    void deleteShippingAddress(DeleteShippingAddressRequestDto deleteShippingAddressRequestDto);

    void updateShippingAddress(UUID shippingAddressId, UpdateShippingAddressDto updateShippingAddressDto);
}
