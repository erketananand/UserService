package com.ketan.userservice.services;

import com.ketan.userservice.dtos.UserRequestDto;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.User;
import com.ketan.userservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
@Primary
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDto userRequestDto) {
        // we can add validation and password encryption here if we want
        User user = new User(userRequestDto.getEmail(), userRequestDto.getPassword());
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }
}