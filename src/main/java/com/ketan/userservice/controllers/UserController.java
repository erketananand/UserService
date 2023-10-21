package com.ketan.userservice.controllers;

import com.ketan.userservice.dtos.UserDto;
import com.ketan.userservice.dtos.UserRequestDto;
import com.ketan.userservice.dtos.UserRolesDto;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.User;
import com.ketan.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserDetail(@PathVariable String email) throws NotFoundException {
        UserDto userDto = userService.getUserDetail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{email}/roles")
    public ResponseEntity<UserDto> addRoleToUser(@PathVariable String email, @RequestBody UserRolesDto userRoles) throws NotFoundException {
        UserDto userDto = userService.addRoleToUser(email, userRoles.getRoles());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}

