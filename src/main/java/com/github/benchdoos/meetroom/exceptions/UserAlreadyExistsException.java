package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotBlank;

/**
 * Exception thrown if user with given credentials already exists
 */
@ResponseStatus(code= HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(@NotBlank String username) {
        super("User with given credentials already exists: " + username);
    }
}
