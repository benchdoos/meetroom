package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.dto.EditUserUsernameDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.PermissionDeniedForAction;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.service.UserService;
import com.github.benchdoos.meetroom.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/change-username")
    public UserPublicInfoDto changeUserName(@RequestBody @Valid EditUserUsernameDto editUserUsernameDto,
                                            @NotNull Principal principal) {
        final String MANAGE_AUTHORITY = "MANAGE_USERS:USE";
        if (ObjectUtils.nullSafeEquals(editUserUsernameDto.getOldUsername(), principal.getName()) ||
                UserUtils.hasAnyAuthority(principal, MANAGE_AUTHORITY)) {
            return userService.updateUserUsername(editUserUsernameDto);
        }

        throw new PermissionDeniedForAction(MANAGE_AUTHORITY);
    }
}
