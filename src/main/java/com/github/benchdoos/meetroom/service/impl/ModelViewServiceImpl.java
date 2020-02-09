package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.DateRange;
import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import com.github.benchdoos.meetroom.service.EventService;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import com.github.benchdoos.meetroom.service.ModelViewService;
import com.github.benchdoos.meetroom.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ModelViewServiceImpl implements ModelViewService {
    private final MeetingRoomService roomService;
    private final EventService eventService;

    @Override
    public String getAllRooms(Pageable pageable, Model model) {
        final Page<MeetingRoom> meetingRooms = roomService.findAll(pageable);

        return prepareListRoomPage(model, meetingRooms);
    }

    @Override
    public String getAllAvailable(Pageable pageable, Model model) {
        final Page<MeetingRoom> meetingRooms = roomService.getAllAvailable(pageable);

        return prepareListRoomPage(model, meetingRooms);
    }

    @Override
    public String getMeetingRoomById(UUID uuid, Model model) {
        return getMeetingRoomById(uuid, null, model);
    }

    @Override
    public String getMeetingRoomById(UUID uuid,
                                     ZonedDateTime day,
                                     Model model) {
        final MeetingRoom meetingRoom = roomService.getById(uuid);

        final DateRange dateRange = DateUtils.getWeekRange(day != null ? day : ZonedDateTime.now());

        final List<Event> events = eventService.getEvents(
                meetingRoom,
                dateRange.getFromDate(),
                dateRange.getToDate()
        );

        model.addAttribute("room", meetingRoom);
        model.addAttribute("events", events);
        model.addAttribute("dateRange", dateRange);
        model.addAttribute("prevWeek", DateUtils.getWeekRange(dateRange.getFromDate().minusDays(1)));
        model.addAttribute("nextWeek", DateUtils.getWeekRange(dateRange.getToDate().plusDays(1)));
        return "meeting-room.html";
    }

    @Override
    public String getMeetingEventInfoById(UUID id, Model model) {
        final EventDto eventDto = eventService.getEventDtoById(id);

        model.addAttribute("event", eventDto);

        return "event.html";
    }

    @Override
    public String createEvent(CreateEventDto createEventDto, Model model) {
        final Event event = eventService.createEvent(createEventDto);

        return getMeetingEventInfoById(event.getId(), model);
    }

    @Override
    public String updateEvent(UUID id, UpdateEventDto updateEventDto, Model model) {
        final Event event = eventService.updateEvent(id, updateEventDto);

        return getMeetingEventInfoById(event.getId(), model);
    }

    @Override
    public String deleteEvent(UUID id, Model model, HttpServletRequest request) {
        final Event event = eventService.getEventById(id);
        @NotNull final ZonedDateTime fromDate = event.getFromDate();
        @NotNull final MeetingRoom meetingRoom = event.getMeetingRoom();
        final boolean deleted = eventService.deleteEvent(id);
        log.info("Event with id: {} is deleted: {}", id, deleted);

        return getMeetingRoomById(meetingRoom.getId(), fromDate, model);

    }

    /**
     * Fills model with rooms and returns list-rooms page name
     *
     * @param model model to fill
     * @param meetingRooms list of rooms
     * @return page
     */
    private String prepareListRoomPage(Model model, Page<MeetingRoom> meetingRooms) {
        model.addAttribute("rooms", meetingRooms);
        return "list-rooms.html";
    }
}
