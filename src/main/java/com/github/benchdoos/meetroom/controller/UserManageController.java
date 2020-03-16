package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditRolesForUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditUserEnableDto;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/manage/users")
public class UserManageController {

    private static final String DEFAULT_SORTING_FIELD = "lastName";

    private final UserService userService;

    private final UserRoleService userRoleService;

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @GetMapping
    public String getMainPage(@PageableDefault(sort = DEFAULT_SORTING_FIELD, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        final Page<User> allUsers = userService.getAllUsers(pageable);
        return prepareManageUserPage(model, allUsers);
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @GetMapping("/search")
    public String searchUsers(@PathParam("request") String request,
                              @PageableDefault(sort = DEFAULT_SORTING_FIELD, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        final Page<User> usersBySearch = userService.searchByUsernameAndNames(request, pageable);
        return prepareManageUserPage(model, usersBySearch);
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

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @PostMapping("/roles/{id}")
    public String editUserRoles(@PathVariable("id") UUID id,
                                @ModelAttribute @Valid EditRolesForUserDto editRolesForUserDto,
                                Principal principal) {

        userService.updateUserRoles(id, editRolesForUserDto, principal);

        return "redirect:/manage/users";
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE')")
    @PostMapping("/reset-password/{id}")
    public String resetUserPassword(@PathVariable("id") UUID id, Principal principal) {

        userService.callForUserPasswordReset(id, principal);

        return "redirect:/manage/users";
    }

    @PreAuthorize(("hasAnyAuthority('MANAGE_USERS:USE')"))
    @PostMapping("/switch-enable/{id}")
    public String updateUserEnabled(@PathVariable("id") UUID id,
                                    @RequestParam boolean enabled,
                                    Principal principal) {

        userService.updateUserEnable(id, enabled, principal);

        return "redirect:/manage/users";
    }


    /**
     * Creates manage page with given users
     *
     * @param model model
     * @param users users to show
     * @return manage users page
     */
    private String prepareManageUserPage(Model model, Page<User> users) {
        final List<UserRole> allUserRoles = userRoleService.getAllUserRoles(Sort.by(Sort.Order.asc("name")));

        model.addAttribute("users", users);
        model.addAttribute("roles", allUserRoles);
        model.addAttribute("editRolesForUserDto", new EditRolesForUserDto());
        model.addAttribute("editUserEnableDto", new EditUserEnableDto());
        return "manage/users";
    }
}
