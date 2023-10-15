package com.ketan.userservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginRequestDto {

    private String email;
    private String encPassword;
}
