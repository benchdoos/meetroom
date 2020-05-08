package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
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
     * Get event by id
     *
     * @param id id
     * @return event
     */
    Event getEventById(UUID id);

    /**
     * Create event
     *
     * @param createEventDto DTO
     * @return created event
     */
    Event createEvent(CreateEventDto createEventDto);

    /**
     * Update event
     *
     * @param id of event
     * @param updateEventDto DTO with data to update
     * @return updated event
     */
    Event updateEvent(UUID id, UpdateEventDto updateEventDto);

    /**
     * Marks event as deleted
     *
     * @param id of event
     * @return boolean, {@code true} is successfully deleted
     */
    boolean deleteEvent(UUID id);

    /**
     * Get current events for user
     *
     * @param userId user id
     * @return events
     */
    List<EventDto> getCurrentEventsForUser(UUID userId);

    /**
     * Get future user's events
     *
     * @param userId id of user
     * @param pageable pageable
     * @return events if there are any future events, or empty list
     */
    Page<EventDto> getFutureEventsForUser(UUID userId, Pageable pageable);
}
