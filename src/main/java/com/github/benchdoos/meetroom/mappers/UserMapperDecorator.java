package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.service.AvatarGravatarService;
import com.github.benchdoos.meetroom.service.AvatarService;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    private AvatarGravatarService gravatarService;

    @Autowired
    private AvatarService avatarService;

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


    @Override
    public void convertAvatar(Avatar avatar, @MappingTarget UserAvatarDto userAvatarDto) {
        if (avatar != null) {
            userAvatarDto.setType(avatar.getType());

            if (avatar.getType().equals(AvatarDataType.GRAVATAR)) {
                userAvatarDto.setSrc(gravatarService.getAvatarByEmail(avatar.getData()).getSrc());
            } else {
                userAvatarDto.setSrc(avatar.getData());
            }
        } else {
            //returns default avatar instead
            final Avatar defaultAvatar = avatarService.getDefaultUserAvatar();
            if (defaultAvatar != null) {
                convertAvatar(defaultAvatar, userAvatarDto);
            }
        }
    }

    /**
     * Converts user avatar to {@link UserAvatarDto} that can be used at frontend. Base64 still not modified.
     * Gravatar avatars will be changed from email into link to gravatar image.
     *
     * @param user to convert from
     * @param userPublicInfoDto dto to convert into
     */
    private void convertUserAvatar(User user, @MappingTarget UserPublicInfoDto userPublicInfoDto) {
        final Avatar avatar = user.getAvatar();
        final UserAvatarDto userAvatarDto = new UserAvatarDto();

        convertAvatar(avatar, userAvatarDto);

        userPublicInfoDto.setAvatar(userAvatarDto);
    }
}
