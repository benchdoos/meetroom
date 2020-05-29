package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.service.UserEmailUpdateService;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Public methods (not all), containing main public methods to get access to system
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/public")
public class ApiV1PublicController {
    private final UserService userService;
    private final UserEmailUpdateService userEmailUpdateService;

    /**
     * Register user. Needs activation.
     *
     * @param createUserDto with user credentials
     * @return user info
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping("/registration")
    public UserPublicInfoDto registerUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }

    /**
     * Submit email for update
     *
     * @param emailId email id, one of {@link UserEmailUpdateRequest#getOldEmailConfirmation()},
     * {@link UserEmailUpdateRequest#getNewEmailConfirmation()}
     */
    @PostMapping("/submit-email-update/{emailId}")
    public void submitEmailUpdate(@PathVariable("emailId") UUID emailId) {
        userEmailUpdateService.submitEmailRequest(emailId);
    }
}
