package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.dto.security.TokenDto;
import com.github.benchdoos.meetroom.service.TokenStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory implementation for {@link TokenStorageService}
 *
 * todo: move to mongodb/redis
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class InMemoryTokenStorageServiceImpl implements TokenStorageService {
    private final ConcurrentHashMap<String, TokenDto> accessTokenStore = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public TokenDto getTokenForUser(@NotBlank String username) {
        final TokenDto tokenDto = accessTokenStore.get(username);
        log.trace("Getting token for username: {}, token: {}", username, tokenDto);
        return tokenDto;
    }

    @Override
    public void storeToken(@NotBlank String username, @NotNull TokenDto tokenDto) {
        log.trace("Saving token for user: {}, token: {}", username, tokenDto);
        accessTokenStore.put(username, tokenDto);
    }

    @Override
    public void removeTokenForUser(String username) {
        log.trace("Removing token for user: {}", username);
        accessTokenStore.remove(username);
    }
}
