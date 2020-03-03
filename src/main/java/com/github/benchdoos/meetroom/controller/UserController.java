package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

import static com.github.benchdoos.meetroom.config.constants.SecurityConstants.ROLE_ADMIN;
import static com.github.benchdoos.meetroom.config.constants.SecurityConstants.ROLE_USER;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Secured({ROLE_ADMIN, ROLE_USER})
    @GetMapping
    public String getUserPage(@RequestParam(value = "username", required = false) String username,
                              Model model,
                              Principal principal) {
        final UserExtendedInfoDto userDto;
        if (StringUtils.hasText(username)) {
            userDto = userService.getExtendedUserInfoDtoByUsername(username);
        } else {
            userDto = userService.getExtendedUserInfoDtoByUsername(principal.getName());
        }
        model.addAttribute("user", userDto);
        return "user.html";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("createUserDto") @Valid CreateUserDto createUserDto,
                               Model model,
                               BindingResult bindingResult) {
        userService.createUser(createUserDto, bindingResult);
        //todo add validation here
        return "index.html";
    }
}
