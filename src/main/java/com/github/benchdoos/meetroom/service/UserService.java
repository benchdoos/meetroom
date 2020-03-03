package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.util.UUID;

/**
 * Service that gives ability to operate with {@link User}
 */
public interface UserService extends UserDetailsService {

    /**
     * Get user by username or throws {@link UserNotFoundException}
     *
     * @param username username
     * @return user
     */
    UserPublicInfoDto getUserPublicInfoDtoByUsername(String username);

    /**
     * Get user extended dto
     *
     * @param username username
     * @return user
     */
    UserExtendedInfoDto getExtendedUserInfoDtoByUsername(String username);

    /**
     * Get user by id
     *
     * @param id of user
     * @return user with given id
     */
    User getById(UUID id);

    /**
     * Create user with role {@link SecurityConstants#ROLE_USER}
     *
     * @param createUserDto user to create
     * @param bindingResult
     * @return created user info
     */
    UserPublicInfoDto createUser(CreateUserDto createUserDto, BindingResult bindingResult);
}
