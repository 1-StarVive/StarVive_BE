package com.starbucks.starvive.user.application;

import com.starbucks.starvive.user.dto.in.SignInRequestDto;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;

public interface UserService {

    void signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

} 