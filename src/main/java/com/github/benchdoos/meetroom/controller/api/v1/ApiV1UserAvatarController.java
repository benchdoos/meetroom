package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.exceptions.AvatarCanBeUpdatedByItsOwnerException;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import com.github.benchdoos.meetroom.service.AvatarGravatarService;
import com.github.benchdoos.meetroom.service.UserService;
import com.github.benchdoos.meetroom.utils.UserUtils;
import com.timgroup.jgravatar.GravatarDefaultImage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

/**
 * Rest implementation of user avatar controller.
 * Gives ability to operate with generating avatars,.. etc.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/user-avatar")
public class ApiV1UserAvatarController {
    private final InternalConfiguration configuration;
    private final AvatarGeneratorService avatarGeneratorService;
    private final AvatarGravatarService avatarGravatarService;
    private final UserService userService;

    /**
     * Get randomly generated user avatar
     *
     * @return base64 string with generated image
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/random")
    public UserAvatarDto getRandomAvatar() {
        return avatarGeneratorService.generateRandomAvatar(configuration.getUserSettings().getAvatarSize());
    }

    /**
     * Get avatar by given string
     *
     * @param key for avatar generation
     * @return base64 string with generated image
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/by-key/{key}")
    public UserAvatarDto getRandomAvatar(@PathVariable("key") String key) {
        return avatarGeneratorService.generateAvatarForString(key, configuration.getUserSettings().getAvatarSize());
    }

    /**
     * Get gravatar image url
     *
     * @param email of gravatar user
     * @return gravatar link to image of given email address or randomly generated as {@link GravatarDefaultImage#IDENTICON}
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/gravatar/{email}")
    public UserAvatarDto getGravatarAvatarByEmail(@PathVariable("email") String email) {
        return avatarGravatarService.getAvatarByEmail(email);
    }

    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/by-user/{id}")
    public UserAvatarDto getUserAvatar(@PathVariable("id") UUID id) {
        return userService.getAvatarForUserId(id);
    }

    /**
     * Update user's avatar. Only avatar owner can update its avatar.
     *
     * @param userId user id
     * @param updateUserAvatarDto dto with avatar info
     * @return updated avatar info
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @PostMapping("/update/{userId}")
    public UserAvatarDto updateUserAvatar(@PathVariable("userId") UUID userId,
                                          @RequestBody UpdateUserAvatarDto updateUserAvatarDto,
                                          Principal principal) {
        final boolean owner = UserUtils.checkPrincipalToGivenId(principal, userId);
        if (owner) {
            return userService.updateUserAvatar(userId, updateUserAvatarDto);
        }
        throw new AvatarCanBeUpdatedByItsOwnerException();
    }
}
