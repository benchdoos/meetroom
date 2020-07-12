package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if user email is required
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class EmailMustBeSetException extends RuntimeException {
    public EmailMustBeSetException(String username) {
        super(String.format("User (%s) email must be set firstly.", username));
    }
}
