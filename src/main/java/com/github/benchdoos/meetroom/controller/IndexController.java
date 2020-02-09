package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import static com.github.benchdoos.meetroom.config.constants.SecurityConstants.ROLE_ADMIN;
import static com.github.benchdoos.meetroom.config.constants.SecurityConstants.ROLE_USER;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {
    private final ModelViewService modelViewService;

    @GetMapping
    public String getAllAvailableRooms(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 9) Pageable pageable, Model model) {
        return modelViewService.getAllAvailable(pageable, model);
    }

    @Secured(ROLE_ADMIN)
    @GetMapping("/all")
    public String getAllRooms(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 9) Pageable pageable, Model model) {
        return modelViewService.getAllRooms(pageable, model);
    }

    @Secured({ROLE_ADMIN, ROLE_USER})
    @GetMapping("/{uuid}")
    public String getMeetingRoomById(@PathVariable UUID uuid,

                                     @RequestParam(value = "day", required = false)
                                     @DateTimeFormat(pattern = "dd.MM.yyyy") Date day,

                                     Model model) {

        if (day != null) {
            final ZonedDateTime fromDate = ZonedDateTime.ofInstant(day.toInstant(), ZoneId.systemDefault());
            return modelViewService.getMeetingRoomById(uuid, fromDate, model);
        }

        return modelViewService.getMeetingRoomById(uuid, null, model);
    }
}
