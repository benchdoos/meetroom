package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User principal is not present. Clear cache and retry.")
public class UserPrincipalNotPresentException extends RuntimeException {
}
