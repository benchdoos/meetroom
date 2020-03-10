package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AdminCanNotRemoveAdminRoleForHimself extends RuntimeException {
    public AdminCanNotRemoveAdminRoleForHimself(String username) {
        super("Admin user can not remove admin role for himself. Username: " + username);
    }
}
