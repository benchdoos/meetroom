package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "user", target = "creator")
    void convert(Event event, @MappingTarget EventDto eventDto);

}