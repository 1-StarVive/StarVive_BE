package com.starbucks.starvive.user.presentation;

import com.starbucks.starvive.common.jwt.JwtTokenProvider;
import com.starbucks.starvive.user.application.UserService;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.dto.in.LoginRequestDto;
import com.starbucks.starvive.user.dto.in.UserRequestDto;
import com.starbucks.starvive.user.dto.out.LoginResponseDto;
import com.starbucks.starvive.user.dto.out.UserResponseDto;
import com.starbucks.starvive.user.vo.LoginVo;
import com.starbucks.starvive.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * 회원가입 API
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserVo userVo, @RequestParam String email, @RequestParam String password) {
        UserRequestDto userRequestDto = UserRequestDto.from(userVo, email, password);
        UserResponseDto response = userService.userCreate(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginVo loginVo) {
        log.debug("Login attempt for email: {}", loginVo.getEmail());
        
        // VO를 DTO로 변환
        LoginRequestDto loginRequestDto = LoginRequestDto.from(loginVo);
        
        // 인증 수행
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        log.debug("Authentication successful");
        
        // 인증된 사용자 정보 조회
        UserResponseDto user = userService.findByEmail(loginRequestDto.getEmail());
        log.debug("User found with ID: {}", user.getUserId());
        
        // JWT 토큰 생성
        Map<String, Object> claims = Map.of("uuid", user.getUserId().toString());
        log.debug("Creating token with claims: {}", claims);
        
        String token = jwtTokenProvider.generateToken(claims, (UserDetails)authentication.getPrincipal());
        log.debug("Token generated successfully");
        
        // User 엔티티 조회
        User userEntity = userService.findUserById(user.getUserId());
        return ResponseEntity.ok(LoginResponseDto.from(token, userEntity));
    }
} 