package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.dto.security.TokenDto;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface TokenStorageService {

    /**
     * Returns token if exists and not expired for user
     *
     * @param username of user
     * @return token if present
     */
    @Nullable TokenDto getTokenForUser(@NotBlank String username);

    /**
     * Store token for user
     *
     * @param username of user
     * @param tokenDto to store
     */
    void storeToken(@NotBlank String username, @NotNull TokenDto tokenDto);

    /**
     * Removes token for user
     *
     * @param username of user
     */
    void removeTokenForUser(String username);
}
