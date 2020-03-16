package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.EditUserRoleDto;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import com.github.benchdoos.meetroom.service.UserRoleService;
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
@RequestMapping("/manage/roles")
public class RoleManageController {
    private static final String DEFAULT_SORTING_FIELD = "role";

    private final UserRoleService userRoleService;
    private final PrivilegeService privilegeService;

    @PreAuthorize("hasAnyAuthority('MANAGE_ROLES:USE','MANAGE_ROLES:CREATE','MANAGE_ROLES:UPDATE','MANAGE_ROLES:DELETE')")
    @GetMapping
    public String getMainPage(@PageableDefault(sort = DEFAULT_SORTING_FIELD, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        final Page<UserRole> allUserRoles = userRoleService.findAllUserRoles(pageable);
        final List<Privilege> privileges = privilegeService.getAllPrivileges();

        return prepareMainPage(allUserRoles, privileges, model);
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_ROLES:UPDATE')")
    @PostMapping("/{id}")
    public String updateUserRole(@PathVariable("id") UUID id,
                                 @ModelAttribute @Valid EditUserRoleDto editUserRoleDto) {

        userRoleService.updateUserRole(id, editUserRoleDto);

        return "redirect:/manage/roles";
    }

    private String prepareMainPage(Page<UserRole> allUserRoles, List<Privilege> privileges, Model model) {

        model.addAttribute("roles", allUserRoles);
        model.addAttribute("privileges", privileges);
        return "manage/roles";
    }
}
