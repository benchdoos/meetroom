package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
        final CreateUserDto createUserDto = new CreateUserDto();
        model.addAttribute("createUserDto", createUserDto);
        return "registration";
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE') || !isAuthenticated()")
    @PostMapping(value = "/registration")
    public String registerUser(@ModelAttribute("createUserDto") @Valid CreateUserDto createUserDto,
                               Model model) {
        userService.createUser(createUserDto);
        return "redirect:/";
    }
}
