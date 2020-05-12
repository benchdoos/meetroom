package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.ResetUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.domain.enumirations.UserEventTabType;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.service.AccountActivationService;
import com.github.benchdoos.meetroom.service.AvatarService;
import com.github.benchdoos.meetroom.service.EventService;
import com.github.benchdoos.meetroom.service.PasswordResetRequestService;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final EventService eventService;
    private final UserMapper userMapper;
    private final AvatarService avatarService;
    private final PasswordResetRequestService passwordResetRequestService;
    private final AccountActivationService accountActivationService;

    /**
     * Get user page by principal
     *
     * @param principal principal
     * @param model model
     * @return user page name
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping
    public String getUserPage(Principal principal, Model model) {

        final UserExtendedInfoDto userDto = userService.getUserExtendedInfoDtoByUsername(principal.getName());
        model.addAttribute("user", userDto);
        return "user";
    }

    /**
     * Get user page by username
     *
     * @param username username of user
     * @param model model
     * @return user page name by given username
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/{username}")
    public String getUserPageByUsername(@PathVariable("username") String username, Model model) {

        final UserExtendedInfoDto userDto = userService.getUserExtendedInfoDtoByUsername(username);

        model.addAttribute("user", userDto);
        return "user";
    }

    /**
     * User events page
     *
     * @param username user username
     * @param model model
     * @return user events page
     */
    @PreAuthorize("hasAnyAuthority('USER:USE')")
    @GetMapping("/events/{username}")
    public String getUserEventsPage(@PathVariable("username") String username,
                                    @RequestParam(value = "tab", required = false, defaultValue = "NEXT") UserEventTabType tab,
                                    Pageable pageable,
                                    Model model) {
        final UserPublicInfoDto userDto = userService.getUserPublicInfoDtoByUsername(username);
        model.addAttribute("user", userDto);

        final Page<EventDto> eventsForUser;

        if (tab.equals(UserEventTabType.PREVIOUS)) {
            eventsForUser = eventService.getPreviousEventsForUser(userDto.getId(), pageable);
        } else {
            eventsForUser = eventService.getFutureEventsForUser(userDto.getId(), pageable);
        }

        model.addAttribute("events", eventsForUser);
        return "user-events";
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
                                              @Valid ResetUserPasswordDto resetUserPasswordDto) {

        userService.resetUserPasswordByResetRequest(id, resetUserPasswordDto);

        return "redirect:/login";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id") UUID id) {

        accountActivationService.activateAccount(id);

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
