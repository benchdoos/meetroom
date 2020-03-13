package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

/**
 * Exception thrown when someone want to change user's password
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class OnlyAccountOwnerCanChangePassword extends RuntimeException {

    public OnlyAccountOwnerCanChangePassword(@NotNull String userForPasswordChange, @NotNull String userTriedToChangePassword) {
        super(String.format("Only account owner can change password. " +
                        "(User %s tried to change password for user: %s. Action denied.)",
                userTriedToChangePassword,
                userForPasswordChange));
    }
}
