package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface MeetingRoomMapper {

    void convertEntityToDto(MeetingRoom meetingRoom, @MappingTarget MeetingRoomDto meetingRoomDto);

    void convertDtoToEntity(MeetingRoomDto meetingRoomDto, @MappingTarget MeetingRoom meetingRoom);
}
