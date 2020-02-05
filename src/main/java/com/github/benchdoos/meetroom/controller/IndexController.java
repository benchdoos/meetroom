package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.service.IndexViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {
    private final IndexViewService indexViewService;

    @GetMapping
    public String getAllAvailableRooms(@PageableDefault Pageable pageable, Model model) {
        return indexViewService.getAllAvailable(pageable, model);
    }

    @GetMapping("/all")
    public String getAllRooms(@PageableDefault Pageable pageable, Model model) {
        return indexViewService.getAllRooms(pageable, model);
    }

    @GetMapping("/{uuid}")
    public String getMeetingRoomById(@PathVariable UUID uuid, @PageableDefault Pageable pageable, Model model) {
        return indexViewService.getMeetingRoomById(uuid, pageable, model);
    }
}
