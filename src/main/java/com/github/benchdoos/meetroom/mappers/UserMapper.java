package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserDetailsDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Mapper for {@link User}
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    void convert(User user, @MappingTarget UserPublicInfoDto userPublicInfoDto);

    void convert(UserPublicInfoDto userPublicInfoDto, @MappingTarget User user);

    void convert(User user, @MappingTarget UserExtendedInfoDto userExtendedInfoDto);

    void convert(UserExtendedInfoDto userExtendedInfoDto, @MappingTarget User user);

    void convertAvatar(Avatar avatar, @MappingTarget UserAvatarDto userAvatarDto);

    default void convert(UsernamePasswordAuthenticationToken token, @MappingTarget UserExtendedInfoDto userExtendedInfoDto) {
        final Object principal = token.getPrincipal();
        if (principal instanceof UserDetailsDto) {
            final UserDetailsDto details = (UserDetailsDto) principal;
            userExtendedInfoDto.setId(details.getId());
            userExtendedInfoDto.setUsername(details.getUsername());
            userExtendedInfoDto.setRoles(details.getRoles());
            userExtendedInfoDto.setFirstName(details.getFirstName());
            userExtendedInfoDto.setLastName(details.getLastName());
            userExtendedInfoDto.setEnabled(details.isEnabled());
        }
    }

}
