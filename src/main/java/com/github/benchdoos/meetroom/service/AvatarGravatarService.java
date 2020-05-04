package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Avatar;

/**
 * Service to operate with Gravatar
 * @see <a href="https://gravatar.com/">gravatar.com</a>
 */
public interface AvatarGravatarService {

    /**
     * Gets avatar for Gravatar user email
     * @param email of user
     * @return new avatar instance
     */
    Avatar getAvatarByEmail(String email);
}
