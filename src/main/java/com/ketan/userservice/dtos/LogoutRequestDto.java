package com.ketan.userservice.dtos;

import lombok.*;

@Setter
@Getter
@Builder
public class LogoutRequestDto {
    private String token;
    private String email;
    private Boolean logoutAll;
}
