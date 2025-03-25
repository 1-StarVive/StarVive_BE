package com.starbucks.starvive.user.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {
    @Column(name = "phone_number")
    private String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    public static PhoneNumber of(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        return new PhoneNumber(phoneNumber);
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
            throw new IllegalArgumentException("유효하지 않은 전화번호 형식입니다. (예: 010-1234-5678)");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
} 