package com.ketan.userservice.services;

import com.ketan.userservice.dtos.UserRequestDto;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.User;

public interface UserService {
    User createUser(UserRequestDto user);
    User getUserByEmail(String email) throws NotFoundException;
}
