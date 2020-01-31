package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserShortInfoDto;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;

/**
 * Service that gives ability to operate with {@link User}
 */
public interface UserService {

    /**
     * Get user by username or throws {@link UserNotFoundException}
     * @param username username
     * @return user
     */
    UserShortInfoDto getUserShortInfoDtoByUsername(String username);
}
