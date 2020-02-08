package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.MeetroomProperties;
import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.exceptions.MeetingRoomNotFoundException;
import com.github.benchdoos.meetroom.exceptions.TimeNotAvailableException;
import com.github.benchdoos.meetroom.mappers.EventMapper;
import com.github.benchdoos.meetroom.repository.EventRepository;
import com.github.benchdoos.meetroom.service.EventService;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import com.github.benchdoos.meetroom.service.UserService;
import com.github.benchdoos.meetroom.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final MeetroomProperties properties;
    private final MeetingRoomService meetingRoomService;
    private final UserService userService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<Event> getEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate) {
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

    @Override
    public Event createEvent(CreateEventDto createEventDto) {
        final User user = userService.getById(createEventDto.getUserId());
        final MeetingRoom meetingRoom = meetingRoomService.getById(createEventDto.getMeetingRoomId());

        Assert.isTrue(user.isEnabled(), "User must be enabled to create events");
        Assert.isTrue(meetingRoom.isEnabled(), "Meeting room must be enabled to create events");

        final ZonedDateTime fromDate = DateUtils.truncateSecondsToStart(DateUtils.toZoneDateTime(createEventDto.getFromDate()));
        final ZonedDateTime toDate = DateUtils.truncateSecondsToEnd(DateUtils.toZoneDateTime(createEventDto.getToDate()));

        validateEventDuration(fromDate, toDate);

        final List<Event> events = getEvents(meetingRoom, fromDate, toDate);
        if (!CollectionUtils.isEmpty(events)) {
            throw new TimeNotAvailableException(events.get(0)); //Taking the first one. Not critical
        }

        final Event event = Event.builder()
                .user(user)
                .meetingRoom(meetingRoom)
                .fromDate(fromDate)
                .toDate(toDate)
                .deleted(false)
                .build();

        return eventRepository.save(event);
    }

    /**
     * Check event duration. Duration must be in range of minimum and maximum reservation values.
     * {@code fromDate} must be earlier then {@code toDate}.
     *
     * @param fromDate event begin date
     * @param toDate event end date
     * @see MeetroomProperties
     */
    private void validateEventDuration(ZonedDateTime fromDate, ZonedDateTime toDate) {
        final ChronoUnit chronoUnit = ChronoUnit.MINUTES;
        final long eventDuration = chronoUnit.between(fromDate, toDate);

        Assert.isTrue(eventDuration >= properties.getMinimumReservationValue()
                && eventDuration <= properties.getMaximumReservationValue(),
                String.format("Event duration is not in given range: %s", properties.getViewableDurationString()));

        Assert.isTrue(fromDate.isBefore(toDate), "Start date must be before the end date.");
    }

    /**
     * Create {@link Specification} to look for Events in date range. Returns only events that are already deleted and
     * in date range
     *
     * @param meetingRoom meeting room to look by
     * @param fromDate start of time range
     * @param toDate end of time range
     * @return specification with needed queries
     */
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
