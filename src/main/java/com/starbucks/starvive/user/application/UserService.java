package com.starbucks.starvive.user.application;

import com.starbucks.starvive.user.domain.User;
import com.starbucks.starvive.user.dto.in.UserRequestDto;
import com.starbucks.starvive.user.dto.out.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto userCreate(UserRequestDto userRequestDto);
    UserResponseDto findById(UUID userId);
    UserResponseDto findByEmail(String email);
    List<UserResponseDto> findAll();
    void deleteUser(UUID userId);
    User findUserById(UUID userId);
}
