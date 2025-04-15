package com.starbucks.starvive.shippingaddress.repository;

import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;
import com.starbucks.starvive.shippingaddress.domain.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, UUID> {

    List<ShippingAddress> findByUserUuidAndDeletedFalse(UUID userUuid);

    List<ShippingAddress> findByUserUuidAndDeletedFalseAndShippingAddressIdNot(UUID userUuid, UUID shippingAddressId);

    @Modifying
    @Query("UPDATE ShippingAddress sa SET sa.selectedBase = :isDefault WHERE sa.userUuid = :userId AND sa.shippingAddressId != :defaultAddressId AND sa.deleted = false AND sa.selectedBase = true")
    void updateOtherAddressesSelectedBaseStatus(@Param("userId") UUID userId, @Param("defaultAddressId") UUID defaultAddressId, @Param("isDefault") boolean isDefault);

}
