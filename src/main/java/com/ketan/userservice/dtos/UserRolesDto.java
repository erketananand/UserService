package com.ketan.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRolesDto {
    private List<String> roles;
}
