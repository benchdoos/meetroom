package com.github.benchdoos.meetroom.domain.dto.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Dto to provide authorization for user
 */
@RequiredArgsConstructor
@Getter
public class LoginDto {

    /**
     * Username of user
     */
    @NotNull
    private final String username;

    /**
     * Password of user
     */
    @NotNull
    private final String password;
}
