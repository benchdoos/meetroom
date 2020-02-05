package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.service.IndexViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {
    private final IndexViewService indexViewService;

    @GetMapping
    public String getAllAvailableRooms(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 9) Pageable pageable, Model model) {
        return indexViewService.getAllAvailable(pageable, model);
    }

    @GetMapping("/all")
    public String getAllRooms(@PageableDefault(sort = "name", direction = Sort.Direction.ASC, size = 9) Pageable pageable, Model model) {
        return indexViewService.getAllRooms(pageable, model);
    }


    @GetMapping("/{uuid}")
    public String getMeetingRoomById(@PathVariable UUID uuid,

                                     @RequestParam(value = "day", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime day,

                                     @PageableDefault Pageable pageable,
                                     Model model) {

        return indexViewService.getMeetingRoomById(uuid, day, pageable, model);
    }
}
