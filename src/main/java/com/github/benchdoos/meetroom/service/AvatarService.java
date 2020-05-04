package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Avatar;

/**
 * Service to operate with avatars of users
 */
public interface AvatarService {
    /**
     * Returns default avatar for users without avatars
     *
     * @return default avatar
     */
    Avatar getDefaultUserAvatar();
}
