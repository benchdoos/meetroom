package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PasswordResetRequestNotFoundException extends RuntimeException {
    public PasswordResetRequestNotFoundException() {
        super("Password reset request not found by given credentials");
    }
}
