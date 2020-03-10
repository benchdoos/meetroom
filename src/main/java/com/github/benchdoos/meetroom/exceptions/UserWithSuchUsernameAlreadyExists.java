package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

/**
 * Exception thrown when username is gonna be changed, but user with new username already exists
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserWithSuchUsernameAlreadyExists extends RuntimeException {

    public UserWithSuchUsernameAlreadyExists(@NotNull String username) {
        super("User with given username already exists: " + username);
    }
}
