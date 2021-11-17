package com.noname.mediasteam.controller;

import com.noname.mediasteam.domain.auth.AuthService;
import com.noname.mediasteam.domain.auth.request.LoginRequest;
import com.noname.mediasteam.domain.auth.request.RegisterRequest;
import com.noname.mediasteam.domain.auth.response.RegisterResponse;
import com.noname.mediasteam.domain.auth.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse authentication(@RequestBody LoginRequest loginRequest) {
        return authService.authentication(loginRequest);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

}
