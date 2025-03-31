package com.starbucks.starvive.user.repository;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.UserStatus;
import com.starbucks.starvive.user.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // 이메일로 사용자 찾기
    Optional<User> findByEmail(Email email);
    
    // 닉네임으로 사용자 찾기
    Optional<User> findByNickname(String nickname);
    
    // 이메일과 상태로 사용자 찾기
    Optional<User> findByEmailAndStatus(Email email, UserStatus status);
    
    // 이메일이 존재하는지 확인
    boolean existsByEmail(Email email);
    
    // 닉네임이 존재하는지 확인
    boolean existsByNickname(String nickname);
} 