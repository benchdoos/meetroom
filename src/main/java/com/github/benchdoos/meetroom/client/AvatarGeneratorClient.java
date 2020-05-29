package com.github.benchdoos.meetroom.client;

import javax.validation.constraints.Size;

/**
 * External avatar - generator client
 */
public interface AvatarGeneratorClient {

    /**
     * Get avatar image by key
     *
     * @param key string
     * @param size of avatar image
     * @return avatar image
     */
    byte[] getAvatarByKey(String key, @Size(min = 40, max = 256) int size);
}
