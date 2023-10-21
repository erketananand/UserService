package com.ketan.userservice.controllers;

import com.ketan.userservice.dtos.*;
import com.ketan.userservice.exceptions.InvalidPasswordException;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.*;
import com.ketan.userservice.services.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws NotFoundException, InvalidPasswordException {
        return authService.login(loginRequestDto.getEmail(), loginRequestDto.getEncPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws NotFoundException {
        authService.logout(logoutRequestDto.getEmail(), logoutRequestDto.getToken());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) throws NotFoundException {
        return ResponseEntity.ok(authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword()));
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) throws NotFoundException {
        SessionStatus sessionStatus = authService.validate(validateTokenRequestDto.getEmail(), validateTokenRequestDto.getToken());
        return ResponseEntity.ok(sessionStatus);
    }
}

