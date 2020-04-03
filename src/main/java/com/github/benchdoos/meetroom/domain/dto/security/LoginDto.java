package com.github.benchdoos.meetroom.domain.dto.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class LoginDto {

    @NotNull
    private final String username;

    @NotNull
    private final String password;
}
