package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when {@link PasswordResetRequest} expired
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "This password reset request expired")
public class PasswordResetRequestExpired extends RuntimeException {
}
