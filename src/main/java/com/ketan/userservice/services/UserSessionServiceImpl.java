package com.ketan.userservice.services;

import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.UserSession;
import com.ketan.userservice.repositories.UserSessionRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userSessionService")
@Primary
public class UserSessionServiceImpl implements UserSessionService {
    private UserSessionRepository userSessionRepository;

    public UserSessionServiceImpl(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public UserSession createSession(UserSession userSession) {
        return userSessionRepository.save(userSession);
    }

    @Override
    public UserSession getSessionByToken(String token) throws NotFoundException {
        Optional<UserSession> optionalUserService = userSessionRepository.findByToken(token);
        if (optionalUserService.isPresent()) {
            return optionalUserService.get();
        } else {
            throw new NotFoundException("User session not found with token: " + token);
        }
    }

    @Override
    public String deleteSession(String token, String email, Boolean logoutAll) throws NotFoundException {
        if(logoutAll) {
            // Find all sessions associated with the user's email
            List<UserSession> sessionsToDelete = userSessionRepository.findAllByUserEmail(email);
            if(sessionsToDelete.isEmpty()) {
                throw new NotFoundException("User session not found with email: " + email);
            }

            // Delete the found sessions
            userSessionRepository.deleteAll(sessionsToDelete);
            return "Successfully logged out all sessions for user with email: " + email;
        } else {
            Optional<UserSession> session = userSessionRepository.findByToken(token);
            if (session.isPresent()) {
                userSessionRepository.delete(session.get());
                return "Successfully logged out session with token: " + token + " for user with email: " + email;
            } else {
                throw new NotFoundException("User session not found with token: " + token);
            }
        }
    }


}
