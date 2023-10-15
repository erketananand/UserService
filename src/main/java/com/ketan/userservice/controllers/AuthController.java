package com.ketan.userservice.controllers;

import com.ketan.userservice.dtos.*;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.*;
import com.ketan.userservice.services.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AuthController {
    private UserService userService;
    private UserSessionService userSessionService;

    public AuthController(UserService userService, UserSessionService userSessionService) {
        this.userService = userService;
        this.userSessionService = userSessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) throws NotFoundException {
        User user = userService.getUserByEmail(loginRequestDto.getEmail());
//        if(user != null) {
            if (passwordMatches(user.getEncPassword(), loginRequestDto.getEncPassword())) {
                // Passwords match, create a new session
                UserSession session = new UserSession();
                session.setToken(generateRandomToken());
                session.setUser(user);

                UserSession createdSession = userSessionService.createSession(session);

                return new ResponseEntity<>(createdSession.getToken(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
//        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto logoutRequestDto) throws NotFoundException {
        // Implement logout logic here, e.g., invalidate the current user's session
        // We can use a token or other user identification method for session invalidation
        // Return a success message
        String responseMessage = userSessionService.deleteSession(logoutRequestDto.getToken(), logoutRequestDto.getEmail(), logoutRequestDto.getLogoutAll());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    // Helper method to check if the entered password matches the stored encrypted password
    private boolean passwordMatches(String storedPassword, String enteredPassword) {
        // Implement our password comparison logic (e.g., using a hashing library)
        // Compare storedPassword and enteredPassword
        return storedPassword.equals(enteredPassword);
    }

    // Helper method to generate a random session token
    private String generateRandomToken() {
        // Implement your token generation logic, e.g., using a UUID
        return UUID.randomUUID().toString();
    }
}

