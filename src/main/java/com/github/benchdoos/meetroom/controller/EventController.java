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

/**
 * Events pages controller
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {
    private final ModelViewService modelViewService;

    /**
     * Event page by id
     *
     * @param id id of event
     * @param model model
     * @return page name
     */
    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/{id}")
    public String getDtoById(@PathVariable UUID id, Model model) {
        return modelViewService.getEventInfoById(id, model);
    }

    /**
     * Create event and return page
     *
     * @param createEventDto dto with new event
     * @param model model
     * @return page name
     */
    @PreAuthorize("hasAnyAuthority('EVENT:CREATE')")
    @PostMapping("/create")
    public String createEvent(@Valid CreateEventDto createEventDto, Model model) {
        return modelViewService.createEvent(createEventDto, model);
    }

    /**
     * Edit event by id
     *
     * @param id id of event to edit
     * @param updateEventDto dto with new data for event
     * @param model model
     * @return page name
     */
    @PreAuthorize("hasAnyAuthority('EVENT:EDIT')")
    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable UUID id, @Valid UpdateEventDto updateEventDto, Model model) {
        return modelViewService.updateEvent(id, updateEventDto, model);
    }

    /**
     * Delete event by id
     *
     * @param id if of event
     * @param model model
     * @param principal principal
     * @return page name
     */
    @PreAuthorize("hasAnyAuthority('EVENT:DELETE')")
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable UUID id, Model model, Principal principal) {
        return modelViewService.deleteEvent(id, model, principal);
    }
}
