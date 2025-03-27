package com.starbucks.starvive.user.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Password {
    @Column(name = "password")
    private String value;

    public Password(String value) {
        this.value = value;
    }
} 