package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserPasswordDto;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

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

    /**
     * Registration for user
     *
     * @param createUserDto dto to create user
     * @param model model
     * @return index page
     */
    @PreAuthorize("hasAnyAuthority('MANAGE_USERS:USE') || !isAuthenticated()")
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("createUserDto") @Valid CreateUserDto createUserDto,
                               Model model) {
        userService.createUser(createUserDto);
        return "redirect:/";
    }

    /**
     * Change password for user
     *
     * @param id user id
     * @param updateUserPasswordDto dto with passwords
     * @param principal principal
     * @return user
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password/{id}")
    public String changeUserPassword(@PathVariable("id") UUID id,
                                     @Valid UpdateUserPasswordDto updateUserPasswordDto,
                                     Principal principal) {

        userService.updateUserPassword(id, updateUserPasswordDto, principal);

        return "redirect:/login";
    }
}
