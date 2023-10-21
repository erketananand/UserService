package com.ketan.userservice.controllers;

import com.ketan.userservice.dtos.CreateRoleRequestDto;
import com.ketan.userservice.models.Role;
import com.ketan.userservice.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) {
        return ResponseEntity.ok(roleService.createRole(createRoleRequestDto.getName()));
    }
}
