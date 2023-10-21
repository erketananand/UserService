package com.ketan.userservice.repositories;

import com.ketan.userservice.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByToken(String token);

    List<UserSession> findAllByUserEmail(String email);

    Optional<UserSession> findByTokenAndUser_Id(String token, Long id);
}

