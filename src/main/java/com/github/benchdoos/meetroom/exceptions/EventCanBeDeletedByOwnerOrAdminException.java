package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Event can be deleted only event owner or user with ROLE_ADMIN")
public class EventCanBeDeletedByOwnerOrAdminException extends RuntimeException {

}
