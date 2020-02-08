package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service that gives ability to operate with {@link Event}
 */
public interface EventService {

    /**
     * Get list of events by meeting room and date range.
     *
     * @param meetingRoom meeting room to look by
     * @param fromDate start of date range
     * @param toDate end of date range
     * @return only not marked as deleted values
     */
    List<Event> getEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate);

    /**
     * Get list of events by meeting room.
     * @param meetingRoom meeting room to look by
     * @param pageable pageable
     * @return all meeting room events (even marked as deleted).
     */
    Page<Event> getAllEvents(MeetingRoom meetingRoom, Pageable pageable);

    /**
     * Get event info by id
     *
     * @param id of event
     * @return info about event
     */
    EventDto getEventDtoById(UUID id);

    /**
     * Create event
     *
     * @param createEventDto dto
     * @return created event
     */
    Event createEvent(CreateEventDto createEventDto);
}
