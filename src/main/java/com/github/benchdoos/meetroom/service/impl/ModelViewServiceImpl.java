package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.DateRange;
import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.service.ModelViewService;
import com.github.benchdoos.meetroom.service.EventService;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import com.github.benchdoos.meetroom.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
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
    public String getMeetingRoomById(UUID uuid, Pageable pageable, Model model) {
        return getMeetingRoomById(uuid, null, pageable, model);
    }

    @Override
    public String getMeetingRoomById(UUID uuid,
                                     ZonedDateTime day,
                                     Pageable pageable,
                                     Model model) {
        final MeetingRoom meetingRoom = roomService.getById(uuid);

        final DateRange dateRange = DateUtils.getWeekRange(day != null ? day : ZonedDateTime.now());

        final List<Event> events = eventService.getEvents(
                meetingRoom,
                dateRange.getFromDate(),
                dateRange.getToDate(),
                pageable);

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

    /**
     * Fills model with rooms and returns list-rooms page name
     *
     * @param model model to fill
     * @param meetingRooms list of rooms
     * @return page
     */
    private String prepareListRoomPage(Model model, Page<MeetingRoom> meetingRooms) {
        model.addAttribute("rooms", meetingRooms);

        final int totalPages = meetingRooms.getTotalPages();
        if (totalPages > 1) {
            final List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "list-rooms.html";
    }
}