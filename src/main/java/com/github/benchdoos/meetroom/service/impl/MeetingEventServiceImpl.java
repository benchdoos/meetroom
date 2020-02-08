package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.MeetingEvent;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.repository.MeetingEventRepository;
import com.github.benchdoos.meetroom.service.MeetingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MeetingEventServiceImpl implements MeetingEventService {
    private final MeetingEventRepository meetingEventRepository;

    @Override
    public List<MeetingEvent> getMeetingEvents(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable) {
        final Specification<MeetingEvent> prepareSpecification = prepareMeetingEventSpecification(meetingRoom, fromDate, toDate);
        return meetingEventRepository.findAll(prepareSpecification);
    }

    @Override
    public Page<MeetingEvent> getAllMeetingEvents(MeetingRoom meetingRoom, Pageable pageable) {
        return meetingEventRepository.findByMeetingRoom(meetingRoom, pageable);
    }

    private Specification<MeetingEvent> prepareMeetingEventSpecification(MeetingRoom meetingRoom, ZonedDateTime fromDate, ZonedDateTime toDate) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            final Predicate idPredicate = criteriaBuilder.equal(root.get("meetingRoom"), meetingRoom);

            final Predicate deletedPredicate = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(root.get("deleted").as(Boolean.class)),
                    criteriaBuilder.isTrue(root.get("deleted").as(Boolean.class)));

            final List<Predicate> datePredicates = new ArrayList<>();
            datePredicates.add(criteriaBuilder.between(root.get("fromDate"), fromDate, toDate));
            datePredicates.add(criteriaBuilder.between(root.get("toDate"), fromDate, toDate));

            return criteriaBuilder.and(idPredicate, criteriaBuilder.not(deletedPredicate), criteriaBuilder.or(datePredicates.toArray(new Predicate[0])));
        };
    }
}
