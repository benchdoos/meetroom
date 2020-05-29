package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User role not found by given credentials")
public class RoleNotFoundException extends RuntimeException{
}
