package com.starbucks.starvive.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Wish {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID wishId;

    private String productId;

    private String userId;


     //상태 찜 취소
}
