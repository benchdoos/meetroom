package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user information is trying to be changed by another user
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User information can only be updated by its owner")
public class UserInformationCanOnlyBeUpdatedByItsOwnerException extends RuntimeException {
}
