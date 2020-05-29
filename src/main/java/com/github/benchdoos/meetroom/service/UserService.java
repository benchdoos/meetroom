package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditRolesForUserDto;
import com.github.benchdoos.meetroom.domain.dto.ResetUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserEmailDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserUsernameDto;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
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
    User getUserById(UUID id);

    /**
     * Get user extended info by user id
     *
     * @param id of user
     * @return extended user info
     */
    UserExtendedInfoDto getUserExtendedInfoById(UUID id);

    /**
     * Get user public info by user id
     * @param userId id of user
     * @return public user info
     */
    UserPublicInfoDto getUserPublicInfoDtoById(UUID userId);

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
    UserExtendedInfoDto updateOtherUser(UUID id, EditOtherUserDto editOtherUserDto);

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
     * Update user password with principal check
     *
     * @param id user id
     * @param updateUserPasswordDto dto with new password
     * @param principal principal
     */
    void updateUserPassword(UUID id, UpdateUserPasswordDto updateUserPasswordDto, Principal principal);

    /**
     * Change user password by reset request
     *
     * @param id request id
     * @param resetUserPasswordDto dto with new password
     */
    void resetUserPasswordByResetRequest(UUID id, ResetUserPasswordDto resetUserPasswordDto);

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
     * @param updateUserUsernameDto dto with usernames
     * @return public user info
     */
    UserPublicInfoDto updateUserUsername(UpdateUserUsernameDto updateUserUsernameDto);

    /**
     * Get show-able at frontend avatar
     *
     * @param id user id
     * @return src of avatar
     */
    UserAvatarDto getAvatarForUserId(UUID id);

    /**
     * Update user avatar
     *
     * @param userId id of user
     * @param updateUserAvatar avatar data
     * @return updated avatar dto
     */
    UserAvatarDto updateUserAvatar(UUID userId, UpdateUserAvatarDto updateUserAvatar);

    /**
     * Update user information
     *
     * @param userId id of user
     * @param updateUserInfoDto dto with new information
     * @return updated user info
     */
    UserPublicInfoDto updateUserInfo(UUID userId, UpdateUserInfoDto updateUserInfoDto);

    /**
     * Call for user email update request
     *
     * @param userId user id
     * @param userEmailDto dto with new email address
     */
    void updateUserEmail(UUID userId, UpdateUserEmailDto userEmailDto);
}
