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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        userService.signUp(SignUpRequestDto.from(signUpRequestVo));
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
            return ResponseEntity.ok("Signed out successfully.");
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