package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/user")
public class ApiV1UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @GetMapping("/{id}/full")
    public UserExtendedInfoDto getUserExtendedInfoById(@PathVariable("id") UUID id) {
        return userService.getUserExtendedInfoById(id);
    }

    @GetMapping("/{id}")
    public UserPublicInfoDto getUserInfoById(@PathVariable("id") UUID id) {
        @NotNull final String username = userService.getUserExtendedInfoById(id).getUsername();
        return userService.getUserPublicInfoDtoByUsername(username);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserExtendedInfoDto getMe(UsernamePasswordAuthenticationToken principal) {
        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();

        userMapper.convert(principal, userExtendedInfoDto);

        return userExtendedInfoDto;
    }
}
