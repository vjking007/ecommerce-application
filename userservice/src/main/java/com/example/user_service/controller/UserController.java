package com.example.user_service.controller;

import com.example.user_service.dto.UserResponse;
import com.example.user_service.security.AuthService;
import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

//    @GetMapping("/profile")
//    public ResponseEntity<UserResponse> profile(Authentication authentication) {
//        return ResponseEntity.ok(authService.getProfile(authentication.getName()));
//    }
}
