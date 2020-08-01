package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.EditMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for {@link MeetingRoom}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MeetingRoomMapper {

    void convert(MeetingRoom meetingRoom, @MappingTarget CreateMeetingRoomDto createMeetingRoomDto);

    void convert(MeetingRoom meetingRoom, @MappingTarget EditMeetingRoomDto editMeetingRoomDto);

    void convert(MeetingRoom meetingRoom, @MappingTarget MeetingRoomDto meetingRoomDto);

    void convert(EditMeetingRoomDto editMeetingRoomDto, @MappingTarget MeetingRoom meetingRoom);

    void convert(CreateMeetingRoomDto createMeetingRoomDto, @MappingTarget MeetingRoom meetingRoom);
}
