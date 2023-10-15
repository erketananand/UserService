package com.ketan.userservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseModel {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String encPassword;

}
