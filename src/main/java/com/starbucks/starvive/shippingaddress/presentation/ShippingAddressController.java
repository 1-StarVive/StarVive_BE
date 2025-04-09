package com.starbucks.starvive.shippingaddress.presentation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starbucks.starvive.shippingaddress.dto.in.AddShippingAddressDto;

@RestController
@RequestMapping("/shipping-address")
public class ShippingAddressController {
    
    @PostMapping
    public void addShippingAddress(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddShippingAddressDto addShippingAddressDto) {
        
    }
}
