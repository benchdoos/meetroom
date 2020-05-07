package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserUsernameDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.PermissionDeniedForAction;
import com.github.benchdoos.meetroom.exceptions.UserInformationCanOnlyBeUpdatedByItsOwner;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.service.UserService;
import com.github.benchdoos.meetroom.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.UUID;

/**
 * Rest user controller implementation.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/user")
public class ApiV1UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Get extended info about user by id
     *
     * @param id user id
     * @return info
     */
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @GetMapping("/{id}/full")
    public UserExtendedInfoDto getUserExtendedInfoById(@PathVariable("id") UUID id) {
        return userService.getUserExtendedInfoById(id);
    }

    /**
     * Get public user info by user id
     *
     * @param id user id
     * @return info
     */
    @GetMapping("/{id}")
    public UserPublicInfoDto getUserInfoById(@PathVariable("id") UUID id) {
        @NotNull final String username = userService.getUserExtendedInfoById(id).getUsername();
        return userService.getUserPublicInfoDtoByUsername(username);
    }

    /**
     * Get extended user information by user token
     *
     * @param token user token
     * @return extended user info
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserExtendedInfoDto getMe(UsernamePasswordAuthenticationToken token) {
        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();

        userMapper.convert(token, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @PreAuthorize("hasAnyAuthority('USER:USE', 'MANAGE_USERS:USE')")
    @PostMapping("/update-username")
    public UserPublicInfoDto updateUserName(@RequestBody @Valid UpdateUserUsernameDto updateUserUsernameDto,
                                            @NotNull HttpServletRequest httpServletRequest,
                                            @NotNull Principal principal) {
        final String MANAGE_AUTHORITY = "MANAGE_USERS:USE";
        if (ObjectUtils.nullSafeEquals(updateUserUsernameDto.getOldUsername(), principal.getName()) ||
                UserUtils.hasAnyAuthority(principal, MANAGE_AUTHORITY)) {
            final UserPublicInfoDto userPublicInfoDto = userService.updateUserUsername(updateUserUsernameDto);

            logout(httpServletRequest); //need to log out if current user is online

            return userPublicInfoDto;
        }

        throw new PermissionDeniedForAction(MANAGE_AUTHORITY);
    }

    /***
     * Update user password. Requesting user must be the same as the given one.
     *
     * @param userId id of user
     * @param updateUserPasswordDto dto with password info
     * @param principal principal
     * @return public user info
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @PostMapping("/update-password/{userId}")
    public UserPublicInfoDto updateUserPassword(@PathVariable("userId") UUID userId,
                                                @RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto,
                                                @NotNull Principal principal) {

        userService.updateUserPassword(userId, updateUserPasswordDto, principal);

        return userService.getUserPublicInfoDtoById(userId);
    }

    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @PostMapping("/update/{userId}")
    public UserPublicInfoDto updateUserInfo(@PathVariable("userId") UUID userId,
                                            @RequestBody @Valid UpdateUserInfoDto updateUserInfoDto,
                                            Principal principal) {

        final boolean owner = UserUtils.checkPrincipalToGivenId(principal, userId);
        if (owner) {
            return userService.updateUserInfo(userId, updateUserInfoDto);
        }
        throw new UserInformationCanOnlyBeUpdatedByItsOwner();
    }


    /**
     * Logs out user by given request
     *
     * @param request http request
     */
    private void logout(HttpServletRequest request) {
        new SecurityContextLogoutHandler().logout(request, null, null);
    }
}
