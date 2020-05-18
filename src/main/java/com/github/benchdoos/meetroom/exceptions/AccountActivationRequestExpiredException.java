package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when account activation request is already expired
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "This account activation request expired")
public class AccountActivationRequestExpiredException extends RuntimeException {

}
