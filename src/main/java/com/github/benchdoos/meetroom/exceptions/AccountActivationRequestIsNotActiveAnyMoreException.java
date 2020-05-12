package com.github.benchdoos.meetroom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "This account activation request is not active anymore")
public class AccountActivationRequestIsNotActiveAnyMoreException extends RuntimeException {

}
