package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.config.properties.ManagePageProperties;
import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {
    private final ModelViewService modelViewService;
    private final ManagePageProperties managePageProperties;

    @GetMapping
    public String getAllAvailableRooms(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 9) Pageable pageable, Model model) {
        return modelViewService.getAllAvailable(pageable, model);
    }

    @PreAuthorize("hasAnyAuthority('LIST_ROOMS:USE')")
    @GetMapping("/all")
    public String getAllRooms(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 9) Pageable pageable, Model model) {
        return modelViewService.getAllRooms(pageable, model);
    }

    /**
     * Manage page
     *
     * @param model model
     * @return manage page
     */
    @PreAuthorize("hasAnyAuthority('MANAGE:USE','MANAGE_USERS:USE')")
    @GetMapping("/manage")
    public String getManagePage(Model model) {
        model.addAttribute("pages", managePageProperties.getPages());
        return "manage";
    }
}
