package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.DateRange;
import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.service.IndexViewService;
import com.github.benchdoos.meetroom.service.MeetingEventService;
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
public class IndexViewServiceImpl implements IndexViewService {
    private final MeetingRoomService roomService;
    private final MeetingEventService eventService;

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
        return getMeetingRoomById(uuid, null, null, pageable, model);
    }

    @Override
    public String getMeetingRoomById(UUID uuid,
                                     ZonedDateTime fromDate,
                                     ZonedDateTime toDate,
                                     Pageable pageable,
                                     Model model) {
        final MeetingRoom meetingRoom = roomService.getById(uuid);

        DateRange dateRange;

        dateRange = new DateRange(fromDate, toDate);

        if (!dateRange.isValid()) {
            dateRange = new DateRange(
                    DateUtils.truncateTime(ZonedDateTime.now()),
                    DateUtils.truncateTime(ZonedDateTime.now().plusDays(7)));
        }

        final Page<MeetingEvent> meetingEvents = eventService.getMeetingEvents(
                meetingRoom,
                dateRange.getFromDate(),
                dateRange.getToDate(),
                pageable);

        model.addAttribute("room", meetingRoom);
        model.addAttribute("events", meetingEvents);
        return "meeting-room.html";
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
