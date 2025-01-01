package com.blog.controller;

import com.blog.dto.JwtAuthResponse;
import com.blog.dto.LoginDto;
import com.blog.dto.SignUpDto;
import com.blog.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
