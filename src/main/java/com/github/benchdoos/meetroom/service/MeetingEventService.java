package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;

public interface MeetingEventService {

    Page<MeetingEvent> getMeetingEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable);

    Page<MeetingEvent> getAllMeetingEvents(MeetingRoom meetingRoom, Pageable pageable);
}
