package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface MeetingEventRepository extends JpaRepository<MeetingEvent, UUID> {

    List<MeetingEvent> findByMeetingRoomAndFromDateIsAfterAndToDateIsBefore(
            MeetingRoom meetingRoom,
            ZonedDateTime fromDate,
            ZonedDateTime toDate,
            Pageable pageable);

    @Query("select e from MeetingEvent e where e.meetingRoom = :room " +
            "and ((e.fromDate >= :from and e.fromDate <= :to) " +
            "or(:from >= e.fromDate and e.toDate <= :from) " +
            "or(:from <= e.toDate and :to >= e.fromDate))")
    List<MeetingEvent> findMeetingRooms(
                                         @Param("room") MeetingRoom meetingRoom,
                                         @Param("from") ZonedDateTime fromDate,
                                         @Param("to") ZonedDateTime toDate);

    Page<MeetingEvent> findByMeetingRoom(MeetingRoom meetingRoom, Pageable pageable);
}
