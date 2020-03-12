package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditUserRoles;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPasswordChangeDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
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
     * @return created user info
     */
    UserPublicInfoDto createUser(CreateUserDto createUserDto);

    /**
     * Get all users in system, including deleted
     *
     * @param pageable pageable
     * @return pageable of all users
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Create other user with random password and disabled account that is needed to be activated
     *
     * @param createOtherUserDto user dto
     */
    void createOtherUser(CreateOtherUserDto createOtherUserDto);

    /**
     * Edit other user
     *
     * @param id user id
     * @param editOtherUserDto user dto
     * @return updated user info
     */
    UserExtendedInfoDto editOtherUser(UUID id, EditOtherUserDto editOtherUserDto);

    /**
     * Edit roles of user
     *
     * @param id user id
     * @param editUserRoles new roles
     * @param principal principal
     * @return updated user with roles
     */
    UserExtendedInfoDto updateUserRoles(UUID id, EditUserRoles editUserRoles, Principal principal);

    /**
     * Call for user password reset.
     *
     * @param id user id
     * @param principal principal
     */
    void callForUserPasswordReset(UUID id, Principal principal);


    /**
     * Change user password
     *
     * @param id user id
     * @param userPasswordChangeDto dto with new password
     */
    void changeUserPassword(UUID id, UserPasswordChangeDto userPasswordChangeDto, Principal principal);

    /**
     * Change user password by reset request
     *
     * @param id request id
     * @param userPasswordChangeDto dto with new password
     */
    void resetUserPasswordByResetRequest(UUID id, UserPasswordChangeDto userPasswordChangeDto);
}
