package com.starbucks.starvive.user.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Email {
    @Column(name = "email")
    private String value;

    public Email(String value) {
        this.value = value;
    }
} 