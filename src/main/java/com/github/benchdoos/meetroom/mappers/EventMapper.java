package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.Event;
import com.github.benchdoos.meetroom.domain.dto.EventDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for {@link Event}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "user", target = "creator") //for example
    @Mapping(target = "creator.avatar", ignore = true)
    EventDto toEventDto(Event event);

    @IterableMapping(qualifiedByName = "toEventDto") // won't work without it
    void convert(List<Event> userCurrentEvents, @MappingTarget List<EventDto> eventDtos);
}
