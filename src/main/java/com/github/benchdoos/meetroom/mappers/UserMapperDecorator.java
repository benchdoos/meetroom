package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.service.AvatarGravatarService;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    private AvatarGravatarService gravatarService;

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public void convert(User user, @MappingTarget UserPublicInfoDto userPublicInfoDto) {
        delegate.convert(user, userPublicInfoDto);
        convertUserAvatar(user, userPublicInfoDto);
    }

    @Override
    public void convert(User user, @MappingTarget UserExtendedInfoDto userExtendedInfoDto) {
        delegate.convert(user, userExtendedInfoDto);
        convertUserAvatar(user, userExtendedInfoDto);
    }

    /**
     * Converts user avatar to string that can be used at frontend. Base64 still not modified.
     * Gravatar avatars will be changed from email into link to gravatar image.
     *
     * @param user to convert from
     * @param userPublicInfoDto dto to convert into
     */
    private void convertUserAvatar(User user, @MappingTarget UserPublicInfoDto userPublicInfoDto) {
        final Avatar avatar = user.getAvatar();
        System.out.println("Avatar: " + avatar);
        if (avatar.getType().equals(AvatarDataType.GRAVATAR)) {
            userPublicInfoDto.setAvatar(gravatarService.getAvatarByEmail(avatar.getData()).getData());
        } else {
            userPublicInfoDto.setAvatar(avatar.getData());
        }
    }
}
