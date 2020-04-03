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
}
