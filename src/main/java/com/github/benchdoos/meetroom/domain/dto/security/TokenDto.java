package com.github.benchdoos.meetroom.domain.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Getter
@ToString
public class TokenDto {
    private final TokenInfoDto token;
    private final TokenInfoDto refreshToken;

    @AllArgsConstructor
    @Getter
    @ToString
    public static class TokenInfoDto{
        private final String token;
        private final Date created;
        private final Date expires;
    }
}
