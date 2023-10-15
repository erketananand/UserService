package com.ketan.userservice.services;

import com.ketan.userservice.exceptions.NotFoundException;
import com.ketan.userservice.models.UserSession;

public interface UserSessionService {
    UserSession createSession(UserSession userSession);
    UserSession getSessionByToken(String token) throws NotFoundException;
    String deleteSession(String token, String email, Boolean logoutAll) throws NotFoundException;
}
