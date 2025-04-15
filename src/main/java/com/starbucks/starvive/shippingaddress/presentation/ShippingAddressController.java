package com.starbucks.starvive.shippingaddress.presentation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import com.starbucks.starvive.shippingaddress.application.ShippingAddressService;
import com.starbucks.starvive.shippingaddress.dto.in.AddShippingAddressDto;
import com.starbucks.starvive.shippingaddress.vo.in.AddShippingAddressVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import com.starbucks.starvive.shippingaddress.vo.in.UpdateShippingAddressVo;
import com.starbucks.starvive.shippingaddress.dto.in.UpdateShippingAddressDto;
import com.starbucks.starvive.shippingaddress.dto.in.DeleteShippingAddressRequestDto;
import com.starbucks.starvive.shippingaddress.vo.in.DeleteShippipngAddressVo;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/shipping-address")
public class ShippingAddressController {
    
    private final ShippingAddressService shippingAddressService;

    @PutMapping
    public void putMethodName(@RequestBody UpdateShippingAddressVo updateShippingAddressVo) {
        shippingAddressService.updateShippingAddress(updateShippingAddressVo.getShippingAddressId(), UpdateShippingAddressDto.from(updateShippingAddressVo));
    }

    @PostMapping
    public void addShippingAddress(@AuthenticationPrincipal UserDetails userDetails, 
    @RequestBody AddShippingAddressVo addShippingAddressVo) {
        AddShippingAddressDto dto = AddShippingAddressDto.from(addShippingAddressVo);
        shippingAddressService.addShippingAddress(dto, userDetails);
    }

    @GetMapping
    public List<ShippingAddress> getShippingAddress(@AuthenticationPrincipal UserDetails userDetails) {
        List<ShippingAddress> shippingAddresses = shippingAddressService.getShippingAddress(userDetails);
        return shippingAddresses;
    }
    
    @DeleteMapping
    public void deleteShippingAddress(@RequestBody DeleteShippipngAddressVo deleteShippipngAddressVo) {
        shippingAddressService.deleteShippingAddress(DeleteShippingAddressRequestDto.from(deleteShippipngAddressVo));
    }
    
}
