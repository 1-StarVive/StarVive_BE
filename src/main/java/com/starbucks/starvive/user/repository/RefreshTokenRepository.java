package com.starbucks.starvive.user.repository;

import com.starbucks.starvive.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    
    Optional<RefreshToken> findByUserId(UUID userId);
    
    void deleteByUserId(UUID userId);
} 