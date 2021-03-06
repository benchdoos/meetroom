package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when password reset request is not active
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "This password reset request is not active anymore")
public class PasswordResetRequestIsNotActiveAnyMoreException extends RuntimeException {
}
