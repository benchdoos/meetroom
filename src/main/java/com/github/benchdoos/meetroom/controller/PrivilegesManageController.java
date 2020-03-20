package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/manage/privileges")
@Controller
public class PrivilegesManageController {
    private final PrivilegeService privilegeService;


    @PreAuthorize("hasAnyAuthority('MANAGE:USE','MANAGE_USERS:USE','MANAGE_ROLES:USE')")
    @GetMapping
    public String getMainPage(Model model) {

        model.addAttribute("privileges", privilegeService.getAllPrivileges());
        return "manage/privileges";
    }

}
