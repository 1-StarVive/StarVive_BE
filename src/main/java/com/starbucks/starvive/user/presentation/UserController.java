package com.starbucks.starvive.user.presentation;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import jakarta.validation.Valid;
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
import com.starbucks.starvive.common.exception.BaseException;
import static com.starbucks.starvive.common.domain.BaseResponseStatus.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignUpRequestVo signUpRequestVo) {

        userService.signUp(SignUpRequestDto.from(signUpRequestVo));
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInRequestVo signInRequestVo) {

        return userService.signIn(SignInRequestDto.from(signInRequestVo));
    }

    @PostMapping("/signout")
    public void signOut(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails instanceof User) {
            userService.signOut(((User) userDetails).getUserId());
        } else {
            throw new BaseException(NO_SIGN_IN);
        }
    }
    
    @PostMapping("/refresh")
    public RefreshResponse refreshAccessToken(@RequestBody RefreshRequest refreshRequest) {

        String requestRefreshToken = refreshRequest.getRefreshToken();
        Optional<String> newAccessToken = userService.refreshAccessToken(requestRefreshToken);

        return newAccessToken
            .map(token -> new RefreshResponse(token, 3600))
            .orElseThrow(() -> new BaseException(TOKEN_NOT_VALID));
    }

    @Getter
    @Setter
    private static class RefreshRequest {

        private String refreshToken;
    }
    
    @Getter
    private static class RefreshResponse {

        private String accessToken;
        private int expiresIn;

        public RefreshResponse(String accessToken, int accessTokenExpirationTime) {
            this.accessToken = accessToken;
            this.expiresIn = accessTokenExpirationTime;
        }
    }
} 