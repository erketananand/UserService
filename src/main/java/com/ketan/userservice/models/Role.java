package com.ketan.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "roles")
@Setter
@Getter
public class Role extends BaseModel {
    private String role;
}
