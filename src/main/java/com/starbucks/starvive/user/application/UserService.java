package com.starbucks.starvive.user.application;

import com.starbucks.starvive.user.dto.in.SignInRequestDto;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.UUID;
import java.util.Optional;

public interface UserService {

    void signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    void signOut(UUID userId);

    Optional<String> refreshAccessToken(String refreshToken);

    UserDetails loadUserByUsername(String username);
} 