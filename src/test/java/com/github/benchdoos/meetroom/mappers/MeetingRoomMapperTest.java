package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.abstracts.AbstractUnitTest;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import org.junit.Ignore;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


public class MeetingRoomMapperTest extends AbstractUnitTest {

    private EventMapper eventMapper;
    private UserMapper userMapper;
    private MeetingRoomMapper meetingRoomMapper;

    public MeetingRoomMapperTest() {
        this.eventMapper = Mappers.getMapper(EventMapper.class);
        this.userMapper = Mappers.getMapper(UserMapper.class);
        this.meetingRoomMapper = Mappers.getMapper(MeetingRoomMapper.class);
    }

    @Test
    public void convertMeetingRoomToCreateMeetingRoomDto() {
        final MeetingRoom testMeetingRoom = easyRandom.nextObject(MeetingRoom.class);
        final CreateMeetingRoomDto correctDto = createCorrectCreateMeetingRoomDto(testMeetingRoom);

        final CreateMeetingRoomDto testCreateMeetingRoomDto = new CreateMeetingRoomDto();
        meetingRoomMapper.convert(testMeetingRoom, testCreateMeetingRoomDto);

        assertThat(testCreateMeetingRoomDto).isNotNull();
        assertThat(testCreateMeetingRoomDto).isEqualTo(correctDto);
    }

    @Ignore
    @Test
    public void convertMeetingRoomToEditMeetingRoomDto() {
    }

    @Ignore
    @Test
    public void convertMeetingRoomToMeetingRoomDto() {
    }

    @Ignore
    @Test
    public void convertEditMeetingRoomDtoToMeetingRoom() {
    }

    private CreateMeetingRoomDto createCorrectCreateMeetingRoomDto(MeetingRoom testMeetingRoom) {
        return CreateMeetingRoomDto.builder()
                .name(testMeetingRoom.getName())
                .location(testMeetingRoom.getLocation())
                .enabled(testMeetingRoom.isEnabled())
                .build();
    }

    @Ignore
    @Test
    public void convertCreateMeetingRoomDtoToMeetingRoom() {
    }
}