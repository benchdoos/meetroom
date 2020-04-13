package com.github.benchdoos.meetroom.domain.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * Token information dto
 */
@AllArgsConstructor
@Getter
@ToString
public class TokenDto {
    private final TokenInfoDto accessToken;
    private final TokenInfoDto refreshToken;

    /**
     * Token extended information dto
     */
    @AllArgsConstructor
    @Getter
    @ToString
    public static class TokenInfoDto{

        /**
         * Token string
         */
        private final String token;

        /**
         * Type of token (token prefix)
         */
        private final String tokenType;

        /**
         * Token creation date
         */
        private final Date created;

        /**
         * Token expire date
         */
        private final Date expires;

        /**
         * Get full token with token type
         *
         * @return full token
         */
        public String getFullToken() {
            return tokenType + " " + token;
        }
    }
}
