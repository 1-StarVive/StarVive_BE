package com.starbucks.starvive.user.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    @Column(name = "password")
    private String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password of(String password) {
        validatePassword(password);
        return new Password(password);
    }

    private static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }
    }

    public boolean matches(String rawPassword) {
        return this.value.equals(rawPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
} 