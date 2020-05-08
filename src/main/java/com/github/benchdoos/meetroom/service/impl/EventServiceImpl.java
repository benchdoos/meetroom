package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.MeetroomProperties;
import com.github.benchdoos.meetroom.domain.DateRange;
import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateEventDto;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateEventDto;
import com.github.benchdoos.meetroom.exceptions.EventNotFoundException;
import com.github.benchdoos.meetroom.exceptions.TimeNotAvailableException;
import com.github.benchdoos.meetroom.mappers.EventMapper;
import com.github.benchdoos.meetroom.repository.EventRepository;
import com.github.benchdoos.meetroom.service.EventService;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import com.github.benchdoos.meetroom.service.UserService;
import com.github.benchdoos.meetroom.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
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
        final Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        return eventMapper.toEventDto(event);
    }

    @Override
    public Event getEventById(UUID id) {
        return eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    @Override
    public Event createEvent(CreateEventDto createEventDto) {
        final User user = userService.getUserById(createEventDto.getUserId());
        final MeetingRoom meetingRoom = meetingRoomService.getById(createEventDto.getMeetingRoomId());

        Assert.isTrue(user.isEnabled(), "User must be enabled to create events");
        Assert.isTrue(meetingRoom.isEnabled(), "Meeting room must be enabled to create events");

        final DateRange dateRange = DateUtils.createDateRange(
                DateUtils.toZoneDateTime(createEventDto.getFromDate()),
                DateUtils.toZoneDateTime(createEventDto.getToDate())
        );

        validateEventDuration(dateRange.getFromDate(), dateRange.getToDate());

        final List<Event> events = getEvents(meetingRoom, dateRange.getFromDate(), dateRange.getToDate());
        if (!CollectionUtils.isEmpty(events)) {
            throw new TimeNotAvailableException(events.get(0)); //Taking the first one. Not critical
        }

        final Event event = Event.builder()
                .user(user)
                .meetingRoom(meetingRoom)
                .fromDate(dateRange.getFromDate())
                .toDate(dateRange.getToDate())
                .title(createEventDto.getTitle())
                .description(createEventDto.getDescription())
                .deleted(false)
                .build();

        return eventRepository.save(event);
    }


    @Override
    public Event updateEvent(UUID id, UpdateEventDto updateEventDto) {
        final Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        Assert.isTrue(event.getDeleted() == null || !event.getDeleted(),
                "Deleted event can not be modified.");

        final DateRange dateRange = DateUtils.createDateRange(
                DateUtils.toZoneDateTime(updateEventDto.getFromDate()),
                DateUtils.toZoneDateTime(updateEventDto.getToDate())
        );

        validateEventDuration(dateRange.getFromDate(), dateRange.getToDate());

        final List<Event> events = getEvents(event.getMeetingRoom(), dateRange.getFromDate(), dateRange.getToDate());
        if (!CollectionUtils.isEmpty(events)) {
            //checking if the only found event is the same
            if (events.size() != 1 || !events.get(0).getId().equals(event.getId())) {
                throw new TimeNotAvailableException(events.get(0)); //Taking the first one. Not critical
            }
        }

        event.setFromDate(dateRange.getFromDate());
        event.setToDate(dateRange.getToDate());
        event.setTitle(updateEventDto.getTitle());
        event.setDescription(updateEventDto.getDescription());

        return eventRepository.save(event);
    }

    @Override
    public boolean deleteEvent(UUID id) {
        final Event event = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        event.setDeleted(true);
        final Event save = eventRepository.save(event);
        return save.getDeleted() != null && save.getDeleted();
    }

    @Override
    public List<EventDto> getCurrentEventsForUser(UUID userId) {
        final User user = userService.getUserById(userId);

        final ZonedDateTime now = ZonedDateTime.now();

        final Specification<Event> prepareSpecification = prepareEventSpecificationToFindEventsByUserAndTimeBetweenEventStartAndEnd(user, now);

        final List<Event> userCurrentEvents = eventRepository.findAll(prepareSpecification);

        final List<EventDto> eventDtos = new ArrayList<>();

        eventMapper.convert(userCurrentEvents, eventDtos);

        return eventDtos;
    }

    @Override
    public Page<EventDto> getFutureEventsForUser(UUID userId, Pageable pageable) {
        final User user = userService.getUserById(userId);

        final ZonedDateTime now = ZonedDateTime.now();

        final Specification<Event> prepareSpecification = prepareEventSpecificationToFindEventsByUserSinceGivenTime(user, now);

        final Page<Event> userCurrentEvents = eventRepository.findAll(prepareSpecification, pageable);

        final List<EventDto> eventDtos = new ArrayList<>();

        eventMapper.convert(userCurrentEvents.getContent(), eventDtos);

        return new PageImpl<>(eventDtos, pageable, userCurrentEvents.getTotalElements());
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
        final long eventDuration = DateUtils.getDateRangeDuration(fromDate, toDate);

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

            final Predicate meetingRoomPredicate = criteriaBuilder.equal(root.get("meetingRoom"), meetingRoom);

            final Predicate deletedPredicate = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("deleted").as(Boolean.class)),
                    criteriaBuilder.isTrue(root.get("deleted").as(Boolean.class)));

            final List<Predicate> datePredicates = new ArrayList<>();
            datePredicates.add(criteriaBuilder.between(root.get("fromDate"), fromDate, toDate));
            datePredicates.add(criteriaBuilder.between(root.get("toDate"), fromDate, toDate));

            return criteriaBuilder.and(
                    meetingRoomPredicate,
                    criteriaBuilder.not(deletedPredicate),
                    criteriaBuilder.or(datePredicates.toArray(new Predicate[0])));
        };
    }

    /**
     * Get {@link Specification} for {@link Event} to get events by user for given time, that are not deleted
     *
     * @param user user
     * @param time time
     * @return prepared specification
     */
    private Specification<Event> prepareEventSpecificationToFindEventsByUserAndTimeBetweenEventStartAndEnd(User user, ZonedDateTime time) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            final Predicate userPredicate = criteriaBuilder.equal(root.get("user"), user);

            final Predicate deletedPredicate = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("deleted").as(Boolean.class)),
                    criteriaBuilder.isTrue(root.get("deleted").as(Boolean.class)));


            final Predicate between = criteriaBuilder.between(
                    criteriaBuilder.literal(DateUtils.truncateSecondsToStart(time)),
                    root.get("fromDate"),
                    root.get("toDate"));

            return criteriaBuilder.and(
                    userPredicate,
                    criteriaBuilder.not(deletedPredicate),
                    between);
        };
    }

    /**
     * Get {@link Specification} for {@link Event} to get future starting events from given time
     *
     * @param user user
     * @param time time to find future events starting from this time
     * @return prepared specification
     */
    private Specification<Event> prepareEventSpecificationToFindEventsByUserSinceGivenTime(User user, ZonedDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Predicate userPredicate = criteriaBuilder.equal(root.get("user"), user);

            final Predicate deletedPredicate = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("deleted").as(Boolean.class)),
                    criteriaBuilder.isTrue(root.get("deleted").as(Boolean.class)));


            final Predicate futureTimePredicate = criteriaBuilder
                    .greaterThan(root.get("fromDate"), DateUtils.truncateSecondsToStart(time));

            return criteriaBuilder.and(
                    userPredicate,
                    criteriaBuilder.not(deletedPredicate),
                    futureTimePredicate);
        };
    }
}
