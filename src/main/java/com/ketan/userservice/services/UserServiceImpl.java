package com.ketan.userservice.services;

import com.ketan.userservice.dtos.UserDto;
import com.ketan.userservice.dtos.UserRequestDto;
import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.Role;
import com.ketan.userservice.repositories.RoleRepository;
import com.ketan.userservice.models.User;
import com.ketan.userservice.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userService")
@Primary
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(UserRequestDto userRequestDto) {
        // we can add validation and password encryption here if we want
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setEncPassword(userRequestDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public UserDto getUserDetail(String email) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return UserDto.from(user);
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public UserDto addRoleToUser(String email, List<String> roles) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        List<Role> roleList = roleRepository.findAllByRoleIn(roles);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRoles().addAll(roleList);
            User savedUser = userRepository.save(user);
            return UserDto.from(savedUser);
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }
}