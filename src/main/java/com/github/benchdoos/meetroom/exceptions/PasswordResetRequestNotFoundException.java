package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when {@link PasswordResetRequest} is not found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Password reset request not found by given credentials")
public class PasswordResetRequestNotFoundException extends RuntimeException {
}
