package com.starbucks.starvive.user.presentation;

import com.starbucks.starvive.user.application.UserService;
import com.starbucks.starvive.user.dto.in.UserRequestDto;
import com.starbucks.starvive.user.dto.out.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 등록 API
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto request) {
        UserResponseDto response = userService.userCreate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 사용자 정보 조회 API
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "userId") UUID userId) {
        UserResponseDto response = userService.findById(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 탈퇴 API
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 전체 사용자 목록 조회 API
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> responses = userService.findAll();
        return ResponseEntity.ok(responses);
    }

    /**
     * 현재 로그인한 사용자 정보 조회 API
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        UserResponseDto response = userService.findById(UUID.fromString(userId));
        return ResponseEntity.ok(response);
    }
} 