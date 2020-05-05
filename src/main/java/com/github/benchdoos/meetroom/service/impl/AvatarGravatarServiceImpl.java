package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.service.AvatarGravatarService;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AvatarGravatarServiceImpl implements AvatarGravatarService {
    private final InternalConfiguration internalConfiguration;

    @Override
    public UserAvatarDto getAvatarByEmail(String email) {
        final Gravatar gravatar = new Gravatar()
        .setSize(internalConfiguration.getUserSettings().getAvatarSize())
        .setRating(GravatarRating.GENERAL_AUDIENCES)
        .setDefaultImage(GravatarDefaultImage.IDENTICON);

        final String url = gravatar.getUrl(email);

        return new UserAvatarDto(AvatarDataType.GRAVATAR, url);
    }
}
