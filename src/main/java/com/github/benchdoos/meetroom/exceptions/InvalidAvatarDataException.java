package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidAvatarDataException extends RuntimeException {
    public InvalidAvatarDataException(AvatarDataType type) {
        super(String.format("Avatar data is corrupt. This data is not supported by %s type.", type));
    }
}
