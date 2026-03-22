package com.bookvault.controller;


import com.bookvault.dto.*;
import com.bookvault.service.AuthService;
import com.bookvault.response.ApiResponse;
import com.bookvault.response.AuthResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {

        String token = service.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>(new AuthResponse(token), null)
        );
    }
}