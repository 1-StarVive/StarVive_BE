package com.starbucks.starvive.user.presentation;

import com.starbucks.starvive.user.application.UserService;
import com.starbucks.starvive.user.dto.in.PasswordChangeRequest;
import com.starbucks.starvive.user.dto.in.UserCreateRequest;
import com.starbucks.starvive.user.dto.in.UserUpdateRequest;
import com.starbucks.starvive.user.dto.out.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

//    private final UserService userService;
//
//    /**
//     * 사용자 등록 API
//     */
//    @PostMapping
//    public ResponseEntity<UserResponse> register(@RequestBody UserCreateRequest request) {
//        UserResponse response = userService.register(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    /**
//     * 사용자 정보 조회 API
//     */
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") UUID userId) {
//        UserResponse response = userService.findById(userId);
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * 사용자 정보 수정 API
//     */
//    @PutMapping("/{userId}")
//    public ResponseEntity<UserResponse> updateUser(
//            @PathVariable(name = "userId") UUID userId,
//            @RequestBody UserUpdateRequest request) {
//        UserResponse response = userService.updateProfile(userId, request);
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * 비밀번호 변경 API
//     */
//    @PutMapping("/{userId}/password")
//    public ResponseEntity<Void> changePassword(
//            @PathVariable(name = "userId") UUID userId,
//            @RequestBody PasswordChangeRequest request) {
//        userService.changePassword(userId, request);
//        return ResponseEntity.ok().build();
//    }
//
//    /**
//     * 회원 탈퇴 API
//     */
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") UUID userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }
//
//    /**
//     * 전체 사용자 목록 조회 API
//     */
//    @GetMapping
//    public ResponseEntity<List<UserResponse>> getAllUsers() {
//        List<UserResponse> responses = userService.findAll();
//        return ResponseEntity.ok(responses);
//    }
} 