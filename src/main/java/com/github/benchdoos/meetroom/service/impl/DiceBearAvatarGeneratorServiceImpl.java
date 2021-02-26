package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.client.AvatarGeneratorClient;
import com.github.benchdoos.meetroom.config.constants.UsersConstants;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service("diceBearAvatarGeneratorService")
@RequiredArgsConstructor
public class DiceBearAvatarGeneratorServiceImpl implements AvatarGeneratorService {

    @Qualifier("diceBearAvatarGeneratorClient")
    @Autowired
    private AvatarGeneratorClient avatarGeneratorClient;


    @Override
    public UserAvatarDto generateRandomAvatar(int size) {
        final String seed = UUID.randomUUID().toString();
        return generateAvatarForString(seed, size);
    }

    @Override
    public UserAvatarDto generateAvatarForString(@NotNull String key, int size) {
        final byte[] avatarByKey = avatarGeneratorClient.getAvatarByKey(key, size);
        final String encode = Base64Utils.encodeToString(avatarByKey); //todo fix 3a issue
        return new UserAvatarDto(AvatarDataType.BASE64, UsersConstants.BASE64_PNG_IMAGE_PREFIX + encode);
    }
}
