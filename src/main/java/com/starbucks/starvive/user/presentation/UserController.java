package com.starbucks.starvive.user.presentation;

import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.starbucks.starvive.user.application.UserService;
import com.starbucks.starvive.user.dto.in.SignUpRequestDto;
import com.starbucks.starvive.user.vo.SignUpRequestVo;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequestVo signUpRequestVo) {
        userService.signUp(SignUpRequestDto.from(signUpRequestVo));
    }
} 