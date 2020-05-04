package com.github.benchdoos.meetroom.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface AvatarGeneratorService {

    /**
     * Generates random avatar
     *
     * @param size size of picture
     * @return base64 avatar image
     */
    String generateRandomAvatar(@Size(min = 40, max = 256) int size);

    /**
     * Generates avatar by given key-string
     *
     * @param key string
     * @param size size of picture
     * @return base64 avatar image
     */
    String generateAvatarForString(@NotNull String key, @Size(min = 40, max = 256) int size);
}
