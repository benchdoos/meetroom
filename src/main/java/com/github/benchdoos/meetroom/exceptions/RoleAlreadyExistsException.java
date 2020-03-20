package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.Role;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotBlank;

/**
 * Exception, thrown when {@link Role} already exists
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(@NotBlank String name, @NotBlank String role) {
        super(String.format("User role already exists: %s %s", name,role));
    }
}
