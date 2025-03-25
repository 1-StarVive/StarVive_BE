package com.starbucks.starvive.user.application;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.dto.in.PasswordChangeRequest;
import com.starbucks.starvive.user.dto.in.UserCreateRequest;
import com.starbucks.starvive.user.dto.in.UserUpdateRequest;
import com.starbucks.starvive.user.dto.out.UserResponse;
import com.starbucks.starvive.user.repository.UserRepository;
import com.starbucks.starvive.user.vo.Email;
import com.starbucks.starvive.user.vo.Password;
import com.starbucks.starvive.user.vo.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 등록
     */
    @Transactional
    public UserResponse register(UserCreateRequest userRequest) {
        // 이메일 중복 확인
        Email email = Email.of(userRequest.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        
        // 닉네임 중복 확인
        if (userRepository.existsByNickname(userRequest.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        
        // 사용자 생성
        Password password = Password.of(userRequest.getPassword());
        PhoneNumber phoneNumber = PhoneNumber.of(userRequest.getPhoneNumber());
        
        User user = User.builder()
                .email(email)
                .password(password)
                .name(userRequest.getName())
                .nickname(userRequest.getNickname())
                .phoneNumber(phoneNumber)
                .build();
        
        User savedUser = userRepository.save(user);
        
        return UserResponse.from(savedUser);
    }

    /**
     * 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    public UserResponse findById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        return UserResponse.from(user);
    }

    /**
     * 이메일로 사용자 조회
     */
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(Email.of(email))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        return UserResponse.from(user);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public UserResponse updateProfile(UUID userId, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        // 닉네임 중복 확인 (변경된 경우에만)
        if (!user.getNickname().equals(updateRequest.getNickname()) 
                && userRepository.existsByNickname(updateRequest.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        
        PhoneNumber phoneNumber = PhoneNumber.of(updateRequest.getPhoneNumber());
        user.updateProfile(updateRequest.getNickname(), phoneNumber);
        
        return UserResponse.from(user);
    }

    /**
     * 비밀번호 변경
     */
    @Transactional
    public void changePassword(UUID userId, PasswordChangeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        // 현재 비밀번호 확인
        if (!user.getPassword().matches(request.getCurrentPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        
        Password newPasswordVO = Password.of(request.getNewPassword());
        user.changePassword(newPasswordVO);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        
        user.delete();
    }

    /**
     * 전체 사용자 목록 조회
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        try {
            List<User> users = userRepository.findAll();
            
            return users.stream()
                    .map(user -> {
                        try {
                            return UserResponse.builder()
                                    .userId(user.getUserId())
                                    .email(user.getEmail().getValue())
                                    .name(user.getName())
                                    .nickname(user.getNickname())
                                    .phoneNumber(user.getPhoneNumber().getValue())
                                    .createdAt(user.getCreatedAt())
                                    .updatedAt(user.getUpdatedAt())
                                    .status(user.getStatus())
                                    .build();
                        } catch (Exception e) {
                            throw new RuntimeException("사용자 변환 중 오류 발생: " + e.getMessage(), e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("전체 사용자 목록 조회 중 오류 발생: " + e.getMessage(), e);
        }
    }
} 