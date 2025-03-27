package com.starbucks.starvive.user.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class PhoneNumber {
    @Column(name = "phone_number")
    private String value;

    public PhoneNumber(String value) {
        this.value = value;
    }
} 