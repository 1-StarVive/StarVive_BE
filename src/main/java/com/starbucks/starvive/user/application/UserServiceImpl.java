package com.starbucks.starvive.user.application;

import org.springframework.stereotype.Service;
import com.starbucks.starvive.user.domain.Gender;
import com.starbucks.starvive.user.domain.SocialLoginType;
import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.domain.UserStatus;
import com.starbucks.starvive.user.dto.in.SignInRequestDto;
import com.starbucks.starvive.user.dto.out.SignInResponseDto;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;
import com.starbucks.starvive.user.repository.UserRepository;
import lombok.Generated;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        this.userRepository.save(signUpRequestDto.toEntity());
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        return null;
    }

    @Generated
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
