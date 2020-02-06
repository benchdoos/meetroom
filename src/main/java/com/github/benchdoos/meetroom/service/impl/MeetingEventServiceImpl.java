package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.repository.MeetingEventRepository;
import com.github.benchdoos.meetroom.service.MeetingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingEventServiceImpl implements MeetingEventService {
    private final MeetingEventRepository meetingEventRepository;

    @Override
    public List<MeetingEvent> getMeetingEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable) {
        return meetingEventRepository.findByMeetingRoomAndFromDateGreaterThanEqualAndToDateLessThanEqual(meetingRoom, fromDate, toDate, pageable);
    }

    @Override
    public Page<MeetingEvent> getAllMeetingEvents(MeetingRoom meetingRoom, Pageable pageable) {
        return meetingEventRepository.findByMeetingRoom(meetingRoom, pageable);
    }
}
