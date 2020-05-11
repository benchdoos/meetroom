package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import com.github.benchdoos.meetroom.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * Rest api for operating with events
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/event")
public class ApiV1EventController {

    private final EventService eventService;

    /**
     * Get event info by id
     *
     * @param id of event
     * @return event info
     */
    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/{id}")
    public EventDto getDtoById(@PathVariable UUID id) {
        return eventService.getEventById(id);
    }

    /**
     * Create event
     *
     * @param createEventDto dto with event info
     * @return event info
     */
    @PreAuthorize("hasAnyAuthority('EVENT:CREATE')")
    @PostMapping
    public EventDto createEvent(@Valid CreateEventDto createEventDto) {
        return eventService.createEvent(createEventDto);
    }

    /**
     * Update event
     *
     * @param id of event
     * @param updateEventDto updated event dto
     * @return updated event dto
     */
    @PreAuthorize("hasAnyAuthority('EVENT:EDIT')")
    @PutMapping("/{id}")
    public EventDto updateEvent(@PathVariable UUID id, @Valid UpdateEventDto updateEventDto) {
        return eventService.updateEvent(id, updateEventDto);
    }

    /**
     * Delete event (can be deleted by event owner or admin)
     *
     * @param id of event to delete
     * @param principal principal of user
     * @return true if successfully deleted
     */
    @PreAuthorize("hasAnyAuthority('EVENT:DELETE')")
    @DeleteMapping("/{id}")
    public boolean deleteEvent(@PathVariable UUID id, Principal principal) {
        return eventService.deleteEvent(id, principal);
    }

    /**
     * Get current events for user by id.
     *
     * @param userId user id
     * @return events if there are any current events, or empty list
     */
    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/current/{userId}")
    public List<EventDto> getCurrentEvents(@PathVariable("userId") UUID userId) {
        return eventService.getCurrentEventsForUser(userId);
    }

    /**
     * Get future user's events
     *
     * @param userId id of user
     * @return events if there are any future events, or empty list
     */
    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/future/{userId}")
    public Page<EventDto> getFutureEvents(@PathVariable("userId") UUID userId,
                                          @PageableDefault(sort = "fromDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getFutureEventsForUser(userId, pageable);
    }

    /**
     * Get previous user's events
     *
     * @param userId id of user
     * @return events if there are any past events, or empty list
     */
    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/previous/{userId}")
    public Page<EventDto> getPreviousEvents(@PathVariable("userId") UUID userId,
                                          @PageableDefault(sort = "fromDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getPreviousEventsForUser(userId, pageable);
    }
}
