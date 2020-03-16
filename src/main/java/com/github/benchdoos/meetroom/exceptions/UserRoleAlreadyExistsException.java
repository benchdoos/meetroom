package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotBlank;

/**
 * Exception, thrown when {@link UserRole} already exists
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserRoleAlreadyExistsException extends RuntimeException {
    public UserRoleAlreadyExistsException(@NotBlank String name, @NotBlank String role) {
        super(String.format("User role already exists: %s %s", name,role));
    }
}
