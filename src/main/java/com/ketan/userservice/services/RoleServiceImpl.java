package com.ketan.userservice.services;

import com.ketan.userservice.models.Role;
import com.ketan.userservice.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role createRole(String roleName) {
        Role role = new Role();
        role.setRole(roleName);
        return roleRepository.save(role);
    }
}
