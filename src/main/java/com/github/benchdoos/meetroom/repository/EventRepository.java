package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.projections.EventDtoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {

    List<Event> findByMeetingRoomAndFromDateIsAfterAndToDateIsBefore(
            MeetingRoom meetingRoom,
            ZonedDateTime fromDate,
            ZonedDateTime toDate,
            Pageable pageable);

    Page<Event> findByMeetingRoom(MeetingRoom meetingRoom, Pageable pageable);

    List<Event> findAll(@Nullable Specification<Event> prepareSpecification);

    EventDtoProjection findFirstBy(Specification<EventDtoProjection> prepared);
}
