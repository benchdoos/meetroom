package com.github.benchdoos.meetroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManageController {

    @GetMapping("/users")
    public String manageUsersPage() {
        return "manage/users.html";
    }
}
