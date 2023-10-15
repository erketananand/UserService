package com.ketan.userservice.dtos;

import lombok.*;

@Builder
@Setter
@Getter
public class UserRequestDto {
    private String email;
    private String password;
}
