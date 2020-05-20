package com.github.benchdoos.meetroom.controller.api.v1.manage;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Rest user controller implementation.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/manage/user")
public class ApiV1ManageUserController {
    private final UserService userService;

    /**
     * If account is not activated, admin can send activation request
     *
     * @param userId id of user
     */
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @PostMapping("/send-activation-request/{userId}")
    public void sendUserActivationRequest(@PathVariable("userId") UUID userId) {
        userService.sendAccountActivationRequest(userId);
    }
}
