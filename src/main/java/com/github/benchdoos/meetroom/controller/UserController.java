package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.service.AvatarService;
import com.github.benchdoos.meetroom.service.PasswordResetRequestService;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AvatarService avatarService;
    private final PasswordResetRequestService passwordResetRequestService;

    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping
    public String getUserPage(@RequestParam(value = "username", required = false) String username,
                              Model model,
                              Principal principal) {

        final UserExtendedInfoDto userDto;

        if (StringUtils.hasText(username)) {
            userDto = userService.getUserExtendedInfoDtoByUsername(username);
        } else {
            userDto = userService.getUserExtendedInfoDtoByUsername(principal.getName());
        }
        model.addAttribute("user", userDto);
        if (userDto.getAvatar() == null) {
            appendDefaultUserAvatar(userDto);
        }
        return "user";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/reset-password/{id}")
    public String getResetPasswordByResetRequestPage(@PathVariable("id") UUID id, Model model) {

        model.addAttribute("request", passwordResetRequestService.getById(id));
        model.addAttribute("passwordDto", new UpdateUserPasswordDto());

        return "reset-password";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/reset-password/{id}")
    public String resetPasswordByResetRequest(@PathVariable("id") UUID id,
                                              @Valid UpdateUserPasswordDto updateUserPasswordDto) {

        userService.resetUserPasswordByResetRequest(id, updateUserPasswordDto);

        return "redirect:/login";
    }


    /**
     * Appends default user avatar to userDto
     *
     * @param userDto dto of user
     */
    private void appendDefaultUserAvatar(UserExtendedInfoDto userDto) {
        final Avatar defaultAvatar = avatarService.getDefaultUserAvatar();

        if (defaultAvatar != null) {
            final UserAvatarDto userAvatarDto = new UserAvatarDto();
            userMapper.convertAvatar(defaultAvatar, userAvatarDto);
            userDto.setAvatar(userAvatarDto);
        }
    }
}
