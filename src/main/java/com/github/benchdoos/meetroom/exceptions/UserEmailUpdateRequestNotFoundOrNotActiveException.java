package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if {@link UserEmailUpdateRequest} not found, expired or not active.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email update request not found or expired.")
public class UserEmailUpdateRequestNotFoundOrNotActiveException extends RuntimeException{
}
