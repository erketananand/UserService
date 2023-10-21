package com.ketan.userservice.services;

import com.ketan.userservice.dtos.CreateRoleRequestDto;
import com.ketan.userservice.models.Role;

public interface RoleService {
    Role createRole(String roleName);
}
