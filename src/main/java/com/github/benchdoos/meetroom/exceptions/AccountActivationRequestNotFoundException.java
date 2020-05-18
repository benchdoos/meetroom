package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when account activation request is not found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account activation request not found")
public class AccountActivationRequestNotFoundException extends RuntimeException {
}
