package com.ketan.userservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession extends  BaseModel {
    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date expiresAt;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;
}
