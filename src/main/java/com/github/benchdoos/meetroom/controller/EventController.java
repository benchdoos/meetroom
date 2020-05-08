package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {
    private final ModelViewService modelViewService;

    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/{id}")
    public String getDtoById(@PathVariable UUID id, Model model) {
        return modelViewService.getEventInfoById(id, model);
    }

    @PreAuthorize("hasAnyAuthority('EVENT:CREATE')")
    @PostMapping("/create")
    public String createEvent(@Valid CreateEventDto createEventDto, Model model) {
        return modelViewService.createEvent(createEventDto, model);
    }

    @PreAuthorize("hasAnyAuthority('EVENT:EDIT')")
    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable UUID id, @Valid UpdateEventDto updateEventDto, Model model) {
        return modelViewService.updateEvent(id, updateEventDto, model);
    }

    @PreAuthorize("hasAnyAuthority('EVENT:DELETE')")
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable UUID id, Model model, Principal principal) {
        return modelViewService.deleteEvent(id, model, principal);
    }
}
