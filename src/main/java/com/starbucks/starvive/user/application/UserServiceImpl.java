package com.starbucks.starvive.user.application;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.dto.in.UserRequestDto;
import com.starbucks.starvive.user.dto.out.UserResponseDto;
import com.starbucks.starvive.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 등록
     */
    @Override
    @Transactional
    public UserResponseDto userCreate(UserRequestDto userRequestDto) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        
        // User 엔티티 생성
        User user = userRequestDto.toEntity();
        user.setPassword(encodedPassword);
        
        // 저장
        User savedUser = userRepository.save(user);
        
        // DTO로 변환하여 반환
        return UserResponseDto.fromEntity(savedUser);
    }

    /**
     * 사용자 정보 조회 (DTO)
     */
    @Override
    public UserResponseDto findById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        return UserResponseDto.fromEntity(user);
    }

    /**
     * 사용자 정보 조회 (Entity)
     */
    @Override
    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 이메일로 사용자 조회
     */
    @Override
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        return UserResponseDto.fromEntity(user);
    }

    /**
     * 전체 사용자 목록 조회
     */
    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 회원 탈퇴
     */
    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        userRepository.deleteById(userId);
    }
}