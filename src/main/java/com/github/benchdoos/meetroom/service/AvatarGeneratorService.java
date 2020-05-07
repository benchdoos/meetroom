package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;

import javax.validation.constraints.NotNull;

/**
 * Service that provides different kinds of avatar generation
 */
public interface AvatarGeneratorService {

    /**
     * Generates random avatar
     *
     * @param size size of picture
     * @return base64 avatar image
     */
    UserAvatarDto generateRandomAvatar(int size);

    /**
     * Generates avatar by given key-string
     *
     * @param key string
     * @param size size of picture
     * @return base64 avatar image
     */
    UserAvatarDto generateAvatarForString(@NotNull String key, int size);
}
