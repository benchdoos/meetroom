package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.dto.security.TokenDto;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service to operate with tokens
 */
public interface TokenService {

    /**
     * Create token for user
     *
     * @param userDetails user
     * @return token
     */
    TokenDto createToken(UserDetails userDetails);

    /**
     * Get user by token
     *
     * @param token token string
     * @return user
     */
    UserDetails getUserByToken(String token);

    /**
     * Get username from given token
     *
     * @param token jwt token
     * @return username
     */
    String getUsernameFromToken(String token);

    /**
     * Validate token with given user details
     *
     * @param token token
     * @param userDetails user details
     * @return true if match and token is not expired
     */
    boolean validateToken(String token, UserDetails userDetails);

    /**
     * Checks if token is expired
     *
     * @param token token to validate
     * @return true if expired
     */
    boolean isTokenExpired(String token);
}
