package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
componentModel = "spring")
public interface MeetingRoomMapper {

    void convert(MeetingRoom meetingRoom, @MappingTarget MeetingRoomDto meetingRoomDto);

    void convert(MeetingRoom meetingRoom, @MappingTarget MeetingRoomInfoDto meetingRoomDto);

    void convert(MeetingRoomDto meetingRoomDto, @MappingTarget MeetingRoom meetingRoom);
}
