package com.github.benchdoos.meetroom.controller.api.v1;

import com.github.benchdoos.meetroom.config.constants.ApiConstants;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConstants.API_V1_PATH_PREFIX + "/event")
public class ApiV1EventController {

    private final EventService eventService;

    /**
     * Get current events for user by id.
     *
     * @param userId user id
     * @return events if there are any current events, or empty list
     */
    @PreAuthorize("hasAnyAuthority('EVENT:USE')")
    @GetMapping("/current/{userId}")
    public List<EventDto> getCurrentEvent(@PathVariable("userId") UUID userId) {
        return eventService.getCurrentEventsForUser(userId);
    }
}
