package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.exceptions.MeetingRoomNotFoundException;
import com.github.benchdoos.meetroom.mappers.EventMapper;
import com.github.benchdoos.meetroom.repository.EventRepository;
import com.github.benchdoos.meetroom.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    @Override
    public List<Event> getEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable) {
        final Specification<Event> prepareSpecification = prepareMeetingEventSpecification(meetingRoom, fromDate, toDate);
        return eventRepository.findAll(prepareSpecification);
    }

    @Override
    public Page<Event> getAllEvents(MeetingRoom meetingRoom, Pageable pageable) {
        return eventRepository.findByMeetingRoom(meetingRoom, pageable);
    }

    @Override
    public EventDto getEventDtoById(UUID id) {
        final Event event = eventRepository.findById(id).orElseThrow(MeetingRoomNotFoundException::new);

        final EventDto eventDto = new EventDto();
        eventMapper.convert(event, eventDto);

        return eventDto;
    }

    private Specification<Event> prepareMeetingEventSpecification(MeetingRoom meetingRoom,
                                                                  ZonedDateTime fromDate,
                                                                  ZonedDateTime toDate) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            final Predicate idPredicate = criteriaBuilder.equal(root.get("meetingRoom"), meetingRoom);

            final Predicate deletedPredicate = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("deleted").as(Boolean.class)),
                    criteriaBuilder.isTrue(root.get("deleted").as(Boolean.class)));

            final List<Predicate> datePredicates = new ArrayList<>();
            datePredicates.add(criteriaBuilder.between(root.get("fromDate"), fromDate, toDate));
            datePredicates.add(criteriaBuilder.between(root.get("toDate"), fromDate, toDate));

            return criteriaBuilder.and(
                    idPredicate,
                    criteriaBuilder.not(deletedPredicate),
                    criteriaBuilder.or(datePredicates.toArray(new Predicate[0])));
        };
    }
}
