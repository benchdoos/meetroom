package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.abstracts.AbstractUnitTest;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.EditMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


public class MeetingRoomMapperTest extends AbstractUnitTest {

    private MeetingRoomMapper meetingRoomMapper;

    public MeetingRoomMapperTest() {
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

    @Test
    public void convertEditMeetingRoomDtoToMeetingRoom() {
        final MeetingRoom correctEntity = easyRandom.nextObject(MeetingRoom.class);
        correctEntity.setId(null); //important for this test, EditMeetingRoomDto does not have such information

        final EditMeetingRoomDto correctDto = createCorrectEditMeetingRoomDto(correctEntity);

        final MeetingRoom testEntity = new MeetingRoom();
        meetingRoomMapper.convert(correctDto, testEntity);

        assertThat(testEntity).isNotNull();
        assertThat(testEntity).isEqualTo(correctEntity);
    }

    @Test
    public void convertCreateMeetingRoomDtoToMeetingRoom() {
        final MeetingRoom correctEntity = easyRandom.nextObject(MeetingRoom.class);
        correctEntity.setId(null); //important for this test, EditMeetingRoomDto does not have such information

        final CreateMeetingRoomDto correctDto = createCorrectCreateMeetingRoomDto(correctEntity);

        final MeetingRoom testEntity = new MeetingRoom();
        meetingRoomMapper.convert(correctDto, testEntity);

        assertThat(testEntity).isNotNull();
        assertThat(testEntity).isEqualTo(correctEntity);
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