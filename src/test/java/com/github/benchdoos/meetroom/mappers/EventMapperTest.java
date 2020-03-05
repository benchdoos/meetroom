package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.abstracts.AbstractUnitTest;
import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest extends AbstractUnitTest {
    private EventMapper eventMapper;
    private UserMapper userMapper;
    private MeetingRoomMapper meetingRoomMapper;

    public EventMapperTest() {
        this.eventMapper = Mappers.getMapper(EventMapper.class);
        this.userMapper = Mappers.getMapper(UserMapper.class);
        this.meetingRoomMapper = Mappers.getMapper(MeetingRoomMapper.class);
    }

    @Test
    void convertEventToEventDto() {
        final Event testEvent = easyRandom.nextObject(Event.class);
        final EventDto testEventDto = getCorrectEventDto(testEvent);

        final EventDto testingObject = new EventDto();
        eventMapper.convert(testEvent, testingObject);

        assertThat(testingObject).isEqualTo(testEventDto);
    }


    private EventDto getCorrectEventDto(Event testEvent) {
        final UserPublicInfoDto testUserPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(testEvent.getUser(), testUserPublicInfoDto);

        final MeetingRoomDto meetingRoomDto = new MeetingRoomDto();
        meetingRoomMapper.convert(testEvent.getMeetingRoom(), meetingRoomDto);

        return EventDto.builder()
                .id(testEvent.getId())
                .creator(testUserPublicInfoDto)
                .fromDate(testEvent.getFromDate())
                .toDate(testEvent.getToDate())
                .title(testEvent.getTitle())
                .description(testEvent.getDescription())
                .meetingRoom(meetingRoomDto)
                .deleted(testEvent.getDeleted())
                .build();
    }
}