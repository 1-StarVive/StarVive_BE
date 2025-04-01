package com.starbucks.starvive.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    
    @Id
    @Column(nullable = false)
    private String token;
    
    @Column(nullable = false)
    private UUID userId;
    
    @Column(nullable = false)
    private Instant expiryDate;
    
    public boolean isExpired() {
        return expiryDate.isBefore(Instant.now());
    }

    @Builder
    public RefreshToken(String token, UUID userId, Instant expiryDate) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }
} 