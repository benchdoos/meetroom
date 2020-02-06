package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface MeetingEventRepository extends JpaRepository<MeetingEvent, UUID> {

    List<MeetingEvent> findByMeetingRoomAndFromDateIsAfterAndToDateIsBefore(
            MeetingRoom meetingRoom,
            ZonedDateTime fromDate,
            ZonedDateTime toDate,
            Pageable pageable);

    List<MeetingEvent> findByMeetingRoomAndFromDateGreaterThanEqualAndToDateLessThanEqual( //todo fixme: create from sunday -> monday to test
            MeetingRoom meetingRoom,
            ZonedDateTime fromDate,
            ZonedDateTime toDate,
            Pageable pageable);

    Page<MeetingEvent> findByMeetingRoom(MeetingRoom meetingRoom, Pageable pageable);
}
