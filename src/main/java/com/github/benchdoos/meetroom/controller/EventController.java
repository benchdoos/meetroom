package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @PostMapping("/create")
    public String createEvent(@Valid CreateEventDto createEventDto, Model model) {
        return modelViewService.createEvent(createEventDto, model);
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable UUID id, @Valid UpdateEventDto updateEventDto, Model model) {
        return modelViewService.updateEvent(id, updateEventDto, model);
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable UUID id, Model model, HttpServletRequest request) {
        return modelViewService.deleteEvent(id, model, request);
    }
}
