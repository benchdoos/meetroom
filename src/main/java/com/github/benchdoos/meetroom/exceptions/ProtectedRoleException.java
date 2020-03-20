package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.config.properties.ProtectedDataProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotBlank;

/**
 * Exception, thrown if role is marked as protected in {@link ProtectedDataProperties}
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ProtectedRoleException extends RuntimeException {

    public ProtectedRoleException(@NotBlank String role) {
        super(String.format("This role (%s) is marked as protected. Any changes are forbidden.", role));
    }
}
