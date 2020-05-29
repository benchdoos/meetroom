package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception, thrown if given user credentials are not present
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User with given credentials not found.")
public class IllegalUserCredentialsException extends RuntimeException {

}
