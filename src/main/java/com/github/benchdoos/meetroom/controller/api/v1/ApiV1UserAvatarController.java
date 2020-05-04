package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest implementation of user avatar controller. Gives ability to operate with generating avatars,.. etc.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/user-avatar")
public class ApiV1UserAvatarController {
    private final InternalConfiguration configuration;
    private final AvatarGeneratorService avatarGeneratorService;

    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/random")
    public String getRandomAvatar() {
        return avatarGeneratorService.generateRandomAvatar(configuration.getUserSettings().getAvatarSize());
    }

    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/by-key/{key}")
    public String getRandomAvatar(@PathVariable("key") String key) {
        return avatarGeneratorService.generateAvatarForString(key, configuration.getUserSettings().getAvatarSize());
    }

}
