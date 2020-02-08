package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.UUID;

public interface ModelViewService {

    String getAllRooms(Pageable pageable, Model model);

    String getAllAvailable(Pageable pageable, Model model);

    String getMeetingRoomById(UUID uuid, Pageable pageable, Model model);

    String getMeetingRoomById(UUID uuid, ZonedDateTime fromDate, Pageable pageable, Model model);

    String getMeetingEventInfoById(UUID id, Model model);

    String createEvent(CreateEventDto createEventDto, Model model);

    String updateEvent(UUID id, UpdateEventDto updateEventDto, Model model);

    String deleteEvent(UUID id, Model model, HttpServletRequest request);
}
