package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Service that fills {@link ModelAndView} with data
 */
public interface ModelViewService {

    /**
     * Page to show all rooms
     *
     * @param pageable pageable
     * @param model model
     * @return name of page
     */
    String getAllRooms(Pageable pageable, Model model);

    /**
     * Get all available rooms
     *
     * @param pageable pageable
     * @param model model
     * @return name of page
     */
    String getAllAvailable(Pageable pageable, Model model);

    /**
     * Get meeting room by id for given date week
     *
     * @param id room id
     * @param model model
     * @return name of page
     */
    String getMeetingRoomById(UUID id, ZonedDateTime fromDate, Model model);

    /**
     * Get event by id
     *
     * @param id event id
     * @param model model
     * @return name of page
     */
    String getEventInfoById(UUID id, Model model);

    /**
     * Create event
     *
     * @param createEventDto event dto
     * @param model model
     * @return name of page
     */
    String createEvent(CreateEventDto createEventDto, Model model);

    /**
     * Update event by id
     *
     * @param id event id
     * @param updateEventDto event dto
     * @param model model
     * @return name of page
     */
    String updateEvent(UUID id, UpdateEventDto updateEventDto, Model model);

    /**
     * Delete event by id
     *
     * @param id event id
     * @param model model
     * @param request request
     * @return name of page
     */
    String deleteEvent(UUID id, Model model, HttpServletRequest request);
}
