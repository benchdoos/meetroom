package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.UserShortInfoDto;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    public final UserService userService;

    public UserShortInfoDto getUserByUsername(String username) {
        return userService.getUserShortInfoDtoByUsername(username);
    }
}
