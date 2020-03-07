package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/manage/users")
public class UserManageController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @GetMapping
    public String getMainPage(@PageableDefault Pageable pageable, Model model) {
        final Page<User> allUsers = userService.getAllUsers(pageable);
        model.addAttribute("users", allUsers);
        return "manage/users";
    }

}
