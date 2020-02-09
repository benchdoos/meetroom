package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for {@link User}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
componentModel = "spring")
public interface UserMapper {

    void convert(User user, @MappingTarget UserPublicInfoDto userPublicInfoDto);

    void convert(UserPublicInfoDto userPublicInfoDto, @MappingTarget User user);

    void convert(User user, @MappingTarget UserExtendedInfoDto userExtendedInfoDto);

    void convert(UserExtendedInfoDto userExtendedInfoDto, @MappingTarget User user);
}
