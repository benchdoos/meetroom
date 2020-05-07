package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

/**
 * Exception thrown if given user has no authorities from given list
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PermissionDeniedForActionException extends RuntimeException {

    public PermissionDeniedForActionException(String... authorities) {
        super("User has not any of needed authorities: " + Arrays.toString(authorities));
    }
}
