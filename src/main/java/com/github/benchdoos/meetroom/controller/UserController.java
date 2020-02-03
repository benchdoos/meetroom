package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.UserShortInfoDto;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String getUserPage(@PathParam("username") String username, Model model) {
        final UserShortInfoDto userShortInfoDtoByUsername = userService.getUserShortInfoDtoByUsername(username);
        model.addAttribute("user", userShortInfoDtoByUsername);
        return "user.html";
    }
}
