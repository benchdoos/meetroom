package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditRolesForUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditUserUsernameDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPasswordChangeDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.domain.dto.security.LoginDto;
import com.github.benchdoos.meetroom.exceptions.IllegalUserCredentialsException;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
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
    UserExtendedInfoDto getUserExtendedInfoDtoByUsername(String username);

    /**
     * Get user by id
     *
     * @param id of user
     * @return user with given id
     */
    User getById(UUID id);

    /**
     * Get user extended info by user id
     *
     * @param id of user
     * @return extended user info
     */
    UserExtendedInfoDto getUserExtendedInfoById(UUID id);

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
     * @param editRolesForUserDto new roles
     * @param principal principal
     * @return updated user with roles
     */
    UserExtendedInfoDto updateUserRoles(UUID id, EditRolesForUserDto editRolesForUserDto, Principal principal);

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

    /**
     * Update user enable. User can not update himself.
     *
     * @param id id of user
     * @param enabled enabled
     * @param principal principal
     */
    void updateUserEnable(UUID id, boolean enabled, Principal principal);

    /**
     * Get users by username and last and first names
     *
     * @param request request string
     * @param pageable pageable
     * @return users
     */
    Page<User> searchByUsernameAndNames(String request, Pageable pageable);

    /**
     * Get user by login dto. Returns user if username and password pair match registered user.
     *
     * @param loginDto login dto
     * @return user
     * @throws IllegalUserCredentialsException if user credentials do not match
     */
    UserDetails getUserByLoginDto(LoginDto loginDto);

    /**
     * Update user username
     *
     * @param editUserUsernameDto dto with usernames
     * @return public user info
     */
    UserPublicInfoDto updateUserUsername(EditUserUsernameDto editUserUsernameDto);

    /**
     * Get show-able at frontend avatar
     *
     * @param id user id
     * @return src of avatar
     */
    String getAvatarForUserId(UUID id);
}
