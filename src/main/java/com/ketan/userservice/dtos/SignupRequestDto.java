package com.ketan.userservice.dtos;

import lombok.*;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
}
