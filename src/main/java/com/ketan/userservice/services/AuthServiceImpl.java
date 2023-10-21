package com.ketan.userservice.services;

import com.ketan.userservice.dtos.UserDto;
import com.ketan.userservice.exceptions.InvalidPasswordException;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.Role;
import com.ketan.userservice.models.SessionStatus;
import com.ketan.userservice.models.User;
import com.ketan.userservice.models.UserSession;
import com.ketan.userservice.repositories.UserRepository;
import com.ketan.userservice.repositories.UserSessionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{
    private UserRepository userRepository;
    private UserSessionRepository userSessionRepository;

    public AuthServiceImpl(UserRepository userRepository, UserSessionRepository userSessionRepository) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
    }
    @Override
    public ResponseEntity<UserDto> login(String email, String encPassword) throws NotFoundException, InvalidPasswordException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User not found with email: " + email);
        }
        User user = optionalUser.get();
        if(!user.getEncPassword().equals(encPassword)){
            throw new InvalidPasswordException("Invalid password for user: " + email);
        }
        // String token = UUID.randomUUID().toString();
        // Create JWT token with user information
        String secretKey = "YourSecretKeyWhichCouldNotBeBrokenOrTheft"; // Replace with your secret key
        String token = Jwts.builder()
                .setSubject(email)
                .claim("roles", user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setToken(token);
        userSession.setStatus(SessionStatus.ACTIVE);
        userSessionRepository.save(userSession);
        UserDto userDto = UserDto.from(user);
        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response;
    }

    @Override
    public void logout(String email, String token) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User not found with email: " + email);
        }
        User user = optionalUser.get();
        Optional<UserSession> optionalUserSession = userSessionRepository.findByTokenAndUser_Id(token, user.getId());
        if(optionalUserSession.isEmpty()){
            throw new NotFoundException("User session not found with email: " + email);
        }
        UserSession userSession = optionalUserSession.get();
        userSession.setStatus(SessionStatus.EXPIRED);
        userSessionRepository.save(userSession);
    }

    @Override
    public UserDto signup(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setEncPassword(password);
        user = userRepository.save(user);
        return UserDto.from(user);
    }

    @Override
    public SessionStatus validate(String email, String token) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User not found with email: " + email);
        }
        User user = optionalUser.get();
        Optional<UserSession> optionalUserSession = userSessionRepository.findByTokenAndUser_Id(token, user.getId());
        if(optionalUserSession.isEmpty()){
            throw new NotFoundException("User session not found with email: " + email);
        }
        UserSession userSession = optionalUserSession.get();
        return userSession.getStatus();
    }
}
