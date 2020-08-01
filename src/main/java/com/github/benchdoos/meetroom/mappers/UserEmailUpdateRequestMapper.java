package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import com.github.benchdoos.meetroom.domain.dto.UserEmailUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper to convert {@link UserEmailUpdateRequest}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserEmailUpdateRequestMapper {

    UserEmailUpdateRequestDto toRequestDto(UserEmailUpdateRequest event);
}
