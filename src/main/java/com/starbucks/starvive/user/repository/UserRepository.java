package com.starbucks.starvive.user.repository;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.SocialLoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);
    
    // 이메일이 존재하는지 확인
    boolean existsByEmail(String email);

    // 로그인 아이디로 사용자 찾기
    Optional<User> findByLoginId(String loginId);

    // 소셜 로그인 타입과 소셜 ID로 사용자 찾기
    Optional<User> findBySocialLoginTypeAndSocialId(SocialLoginType socialLoginType, String socialId);

    // UUID(userId)로 사용자 찾기 - 수정됨
    Optional<User> findByUserId(UUID userId);
} 