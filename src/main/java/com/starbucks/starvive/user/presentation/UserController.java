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

} 