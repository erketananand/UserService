package com.ketan.userservice.services;

import com.ketan.userservice.dtos.UserDto;
import com.ketan.userservice.exceptions.InvalidPasswordException;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.SessionStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserDto> login(String email, String encPassword) throws NotFoundException, InvalidPasswordException;

    void logout(String email, String token) throws NotFoundException;

    UserDto signup(String email, String password);

    SessionStatus validate(String email, String token) throws NotFoundException;
}
