package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Avatar can only be updated by its owner")
public class AvatarCanBeUpdatedByItsOwnerException extends RuntimeException {
}
