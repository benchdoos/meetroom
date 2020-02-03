package com.github.benchdoos.meetroom.service.impl;

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
    public String getAllAvailable(Pageable pageable, Model model) {
        final Page<MeetingRoom> meetingRooms = roomService.getAllAvailable(pageable);

        model.addAttribute("rooms", meetingRooms);

        final int totalPages = meetingRooms.getTotalPages();
        if (totalPages > 0) {
            final List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "list-rooms.html";
    }

    @Override
    public String getMeetingRoomById(UUID uuid, Pageable pageable, Model model) {
        final MeetingRoom meetingRoom = roomService.getById(uuid);
        final Page<MeetingEvent> meetingEvents = eventService.getMeetingEvents(
                meetingRoom,
                DateUtils.truncateTime(ZonedDateTime.now()),
                DateUtils.truncateTime(ZonedDateTime.now().plusDays(7)),
                pageable);
        model.addAttribute("room", meetingRoom);
        model.addAttribute("events", meetingEvents);
        return "meeting-room.html";
    }
}
