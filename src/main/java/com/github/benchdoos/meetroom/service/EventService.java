package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface EventService {

    List<Event> getEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable);

    Page<Event> getAllEvents(MeetingRoom meetingRoom, Pageable pageable);

    EventDto getEventDtoById(UUID id);
}
