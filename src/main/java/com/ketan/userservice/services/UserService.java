package com.ketan.userservice.services;

import com.ketan.userservice.dtos.UserDto;
import com.ketan.userservice.dtos.UserRequestDto;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.User;

import java.util.List;

public interface UserService {
    User createUser(UserRequestDto user);
    User getUserByEmail(String email) throws NotFoundException;
    UserDto getUserDetail(String email) throws NotFoundException;

    UserDto addRoleToUser(String email, List<String> roles) throws NotFoundException;
}
