package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown if email is already in use
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Given e-mail is already used by other user")
public class EmailIsAlreadyUsedException extends RuntimeException {

}
