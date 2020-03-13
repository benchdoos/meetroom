package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User can not update such data about himself")
public class UserCanNotUpdateThisDataByHimself extends RuntimeException {
}
