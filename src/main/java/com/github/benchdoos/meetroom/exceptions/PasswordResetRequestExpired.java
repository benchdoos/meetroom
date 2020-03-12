package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PasswordResetRequestExpired extends RuntimeException {
    public PasswordResetRequestExpired() {
        super("This password reset request expired!");
    }
}
