package com.starbucks.starvive.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@Builder
public class Wish {

    @Id
    @GeneratedValue
    private UUID wishId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String productId;

}
