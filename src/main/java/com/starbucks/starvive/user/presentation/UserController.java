package com.starbucks.starvive.user.presentation;

import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import com.starbucks.starvive.user.application.UserService;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.starbucks.starvive.user.dto.in.SignInRequestDto;
import com.starbucks.starvive.user.vo.SignUpRequestVo;
import com.starbucks.starvive.user.vo.SignInRequestVo;
import com.starbucks.starvive.common.exception.TokenRefreshException;
import java.util.UUID;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequestVo signUpRequestVo, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
            .body("입력 값 오류: " + bindingResult.getAllErrors().stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.joining(", ")));
        }

        userService.signUp(SignUpRequestDto.from(signUpRequestVo));
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInRequestVo signInRequestVo) {
        return userService.signIn(SignInRequestDto.from(signInRequestVo));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            UUID userId = user.getUserId();
            userService.signOut(userId);
            return ResponseEntity.ok("로그아웃 성공공");
        } else {
            return ResponseEntity.status(401).body("Unauthorized: Invalid user details type");
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshRequest refreshRequest) {
        String requestRefreshToken = refreshRequest.getRefreshToken();
        
        Optional<String> newAccessToken = userService.refreshAccessToken(requestRefreshToken);

        return newAccessToken
            .map(token -> ResponseEntity.ok(new RefreshResponse(token)))
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    // Inner DTO for Refresh Request
    @Getter
    @Setter
    private static class RefreshRequest {
        private String refreshToken;
    }
    
    // Inner DTO for Refresh Response
    @Getter
    private static class RefreshResponse {
        private String accessToken;
        
        public RefreshResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }
} 