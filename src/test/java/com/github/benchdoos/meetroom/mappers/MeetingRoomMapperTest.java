package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.abstracts.AbstractUnitTest;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.EditMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
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
        final MeetingRoom testEntity = easyRandom.nextObject(MeetingRoom.class);
        final CreateMeetingRoomDto correctDto = createCorrectCreateMeetingRoomDto(testEntity);

        final CreateMeetingRoomDto testDto = new CreateMeetingRoomDto();
        meetingRoomMapper.convert(testEntity, testDto);

        assertThat(testDto).isNotNull();
        assertThat(testDto).isEqualTo(correctDto);
    }

    @Test
    public void convertMeetingRoomToEditMeetingRoomDto() {
        final MeetingRoom testEntity = easyRandom.nextObject(MeetingRoom.class);
        final EditMeetingRoomDto correctDto = createCorrectEditMeetingRoomDto(testEntity);

        final EditMeetingRoomDto testDto = new EditMeetingRoomDto();
        meetingRoomMapper.convert(testEntity, testDto);

        assertThat(testDto).isNotNull();
        assertThat(testDto).isEqualTo(correctDto);
    }

    @Test
    public void convertMeetingRoomToMeetingRoomDto() {
        final MeetingRoom testEntity = easyRandom.nextObject(MeetingRoom.class);
        final MeetingRoomDto correctDto = createCorrectMeetingRoomDto(testEntity);

        final MeetingRoomDto testDto = new MeetingRoomDto();
        meetingRoomMapper.convert(testEntity, testDto);

        assertThat(testDto).isNotNull();
        assertThat(testDto).isEqualTo(correctDto);
    }

    @Ignore
    @Test
    public void convertEditMeetingRoomDtoToMeetingRoom() {
    }

    @Ignore
    @Test
    public void convertCreateMeetingRoomDtoToMeetingRoom() {
    }

    /**
     * Creates correct dto from entity
     *
     * @param entity entity
     * @return correct entity from dto
     */
    private CreateMeetingRoomDto createCorrectCreateMeetingRoomDto(MeetingRoom entity) {
        return CreateMeetingRoomDto.builder()
                .name(entity.getName())
                .location(entity.getLocation())
                .enabled(entity.isEnabled())
                .build();
    }

    /**
     * Creates dto from entity
     *
     * @param entity entity
     * @return correct entity from dto
     */
    private EditMeetingRoomDto createCorrectEditMeetingRoomDto(MeetingRoom entity) {
        return EditMeetingRoomDto.builder()
                .name(entity.getName())
                .location(entity.getLocation())
                .enabled(entity.isEnabled())
                .build();
    }

    /**
     * Creates dto from entity
     *
     * @param testEntity entity
     * @return correct entity from dto
     */
    private MeetingRoomDto createCorrectMeetingRoomDto(MeetingRoom testEntity) {
        return MeetingRoomDto.builder()
                .id(testEntity.getId())
                .name(testEntity.getName())
                .location(testEntity.getLocation())
                .enabled(testEntity.isEnabled())
                .build();
    }
}