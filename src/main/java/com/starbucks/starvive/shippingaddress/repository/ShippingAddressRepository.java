package com.starbucks.starvive.shippingaddress.repository;

import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, UUID> {

    List<ShippingAddress> findByUserUuidAndDeletedFalse(UUID userUuid);

    List<ShippingAddress> findByUserUuidAndDeletedFalseAndShippingAddressIdNot(UUID userUuid, UUID shippingAddressId);

}
