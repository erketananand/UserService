package com.ketan.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid Password")
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }

}
