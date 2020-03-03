package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        final CreateUserDto createUserDto = new CreateUserDto();
        model.addAttribute("createUserDto", createUserDto);
        return "register.html";
    }
}
