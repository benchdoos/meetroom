package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

public interface MeetingEventService {

    List<MeetingEvent> getMeetingEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable);

    Page<MeetingEvent> getAllMeetingEvents(MeetingRoom meetingRoom, Pageable pageable);
}
