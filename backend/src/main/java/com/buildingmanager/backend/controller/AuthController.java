package com.buildingmanager.backend.controller;

import com.buildingmanager.backend.dto.LoginRequest;
import com.buildingmanager.backend.dto.LoginResponse;
import com.buildingmanager.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}