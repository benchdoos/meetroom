package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.config.properties.ProtectedDataProperties;
import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.dto.CreateRoleDto;
import com.github.benchdoos.meetroom.domain.dto.EditRoleDto;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import com.github.benchdoos.meetroom.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private static final String DEFAULT_SORTING_FIELD = "internalName";

    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final ProtectedDataProperties protectedDataProperties;

    @PreAuthorize("hasAnyAuthority('MANAGE_ROLES:USE','MANAGE_ROLES:CREATE','MANAGE_ROLES:UPDATE','MANAGE_ROLES:DELETE')")
    @GetMapping
    public String getMainPage(@PageableDefault(sort = DEFAULT_SORTING_FIELD, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        final Page<Role> allRoles = roleService.findAllRoles(pageable);
        final List<Privilege> privileges = privilegeService.getAllPrivileges();

        return prepareMainPage(allRoles, privileges, model);
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_ROLES:UPDATE')")
    @PostMapping("/{id}")
    public String updateRole(@PathVariable("id") UUID id,
                             @ModelAttribute @Valid EditRoleDto editRoleDto) {

        roleService.updateRole(id, editRoleDto);

        return "redirect:/manage/roles";
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_ROLES:CREATE')")
    @PostMapping("/create")
    public String createRole(@ModelAttribute @Valid CreateRoleDto createRoleDto) {

        roleService.createRole(createRoleDto);

        return "redirect:/manage/roles";
    }

    @PreAuthorize("hasAnyAuthority('MANAGE_ROLES:DELETE')")
    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable("id") UUID id) {

        roleService.deleteRole(id);

        return "redirect:/manage/roles";
    }

    /**
     * Prepare main page for role management, fill all needed information
     *
     * @param roles roles to show
     * @param privileges all privileges in system
     * @param model model
     * @return page
     */
    private String prepareMainPage(Page<Role> roles, List<Privilege> privileges, Model model) {

        model.addAttribute("roles", roles);
        model.addAttribute("privileges", privileges);
        model.addAttribute("protectedRoles", protectedDataProperties.getRoles());
        return "manage/roles";
    }
}
