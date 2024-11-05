package com.dwbh.backend.controller;

import com.dwbh.backend.dto.CreateUserRequest;
import com.dwbh.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "User", description = "회원")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user")
    @Operation(summary = "회원 등록", description = "회원을 등록한다.")
    public ResponseEntity<Void> createUser(
            @Validated @RequestBody CreateUserRequest createUserRequest) {

        userService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}