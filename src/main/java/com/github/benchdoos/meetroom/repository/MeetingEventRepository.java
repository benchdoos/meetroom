package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface MeetingEventRepository extends JpaRepository<MeetingEvent, UUID>, JpaSpecificationExecutor<MeetingEvent> {

    List<MeetingEvent> findByMeetingRoomAndFromDateIsAfterAndToDateIsBefore(
            MeetingRoom meetingRoom,
            ZonedDateTime fromDate,
            ZonedDateTime toDate,
            Pageable pageable);

    Page<MeetingEvent> findByMeetingRoom(MeetingRoom meetingRoom, Pageable pageable);

    List<MeetingEvent> findAll(@Nullable Specification<MeetingEvent> prepareSpecification);
}
