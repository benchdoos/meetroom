package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {
    private final ModelViewService modelViewService;

    @GetMapping("/{id}")
    public String  getDtoById(@PathVariable UUID id, Model model) {
        return modelViewService.getMeetingEventInfoById(id, model);
    }

}
