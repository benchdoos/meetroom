package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.abstracts.AbstractIntegrationCommonTest;
import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class EventMapperTest extends AbstractIntegrationCommonTest {
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MeetingRoomMapper meetingRoomMapper;

    @Test
    public void convertEventToEventDto() {
        final Event testEvent = easyRandom.nextObject(Event.class);
        final EventDto testEventDto = getCorrectEventDto(testEvent);
        testEventDto.getCreator().setAvatar(null); //we are not testing avatars here

        final EventDto testingObject = eventMapper.toEventDto(testEvent);

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