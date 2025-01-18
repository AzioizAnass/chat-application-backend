package com.blog.controller;

import com.blog.dto.JwtAuthResponse;
import com.blog.dto.LoginDto;
import com.blog.dto.SignUpDto;
import com.blog.dto.UserDto;
import com.blog.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthResource {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.authenticateUser(loginDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        return authService.registerUser(signUpDto);
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.getUser(token));
    }
}
