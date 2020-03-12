package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PasswordResetRequestIsNotActiveAnyMore extends RuntimeException {
    public PasswordResetRequestIsNotActiveAnyMore() {
        super("This password reset request is not active anymore");
    }
}
