package com.example.user.controller;

import com.example.user.entity.RefreshToken;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.service.AuthService;
import com.example.user.service.JwtService;
import com.example.user.service.RefreshTokenService;
import com.example.user.utils.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService, UserRepository userRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }


    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
        String token = validateTokenRequest.getToken();
        String username = jwtService.extractUsername(token); // Extract username from token

        // Find the user by username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        // Validate the token with the user details
        boolean isValid = jwtService.isTokenValid(token, user);

        return ResponseEntity.ok(isValid);
    }
}