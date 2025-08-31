package com.example.user_service.controller;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.RegisterRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.security.AuthService;
import com.example.user_service.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        //System.out.println("Hey "+ request);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile(Authentication authentication) {
        return ResponseEntity.ok(authService.getProfile(authentication.getName()));
    }

    //Added for Api Gateway
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        try {
            String username = jwtService.extractUsername(token.replace("Bearer ", ""));
//            return ResponseEntity.ok(Map.of("username", username));
            return ResponseEntity.ok(true);
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            return ResponseEntity.ok(false);
        }
    }
}

