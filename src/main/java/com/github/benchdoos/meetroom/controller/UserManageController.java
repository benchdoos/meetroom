package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.service.UserRoleService;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/manage/users")
public class UserManageController {

    private final UserService userService;

    private final UserRoleService userRoleService;

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @GetMapping
    public String getMainPage(@PageableDefault Pageable pageable, Model model) {
        final Page<User> allUsers = userService.getAllUsers(pageable);
        final List<UserRole> allUserRoles = userRoleService.getAllUserRoles(Sort.by(Sort.Order.asc("name")));

        model.addAttribute("users", allUsers);
        model.addAttribute("roles", allUserRoles);
        return "manage/users";
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @PostMapping("/registration")
    public String registerOtherUser(@ModelAttribute("createOtherUserDto") @Valid CreateOtherUserDto createOtherUserDto,
                                    Model model) {

        userService.createOtherUser(createOtherUserDto);

        return "redirect:/manage/users";
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @PostMapping("/{id}")
    public String editUser(@PathVariable("id") UUID id,
            @ModelAttribute("editUser") @Valid EditOtherUserDto editOtherUserDto) {

        userService.editOtherUser(id, editOtherUserDto);

        return "redirect:/manage/users";
    }

}
