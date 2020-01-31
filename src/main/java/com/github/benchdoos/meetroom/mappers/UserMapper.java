package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserShortInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
componentModel = "spring")
public interface UserMapper {

    void convert(User user, @MappingTarget UserShortInfoDto userShortInfoDto);

    void convert(UserShortInfoDto userShortInfoDto, @MappingTarget User user);
}