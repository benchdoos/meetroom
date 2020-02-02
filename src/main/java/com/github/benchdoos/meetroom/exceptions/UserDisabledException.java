package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserDisabledException extends RuntimeException {
    public UserDisabledException(@NotNull String username) {
        super(String.format("User (username: %s) is disabled.", username));
    }
}
