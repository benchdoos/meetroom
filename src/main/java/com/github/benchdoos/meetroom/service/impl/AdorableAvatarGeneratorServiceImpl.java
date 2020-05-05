package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.client.AvatarGeneratorClient;
import com.github.benchdoos.meetroom.config.constants.UsersConstants;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdorableAvatarGeneratorServiceImpl implements AvatarGeneratorService {
    private final AvatarGeneratorClient avatarGeneratorClient;

    @Override
    public UserAvatarDto generateRandomAvatar(int size) {
        final String key = UUID.randomUUID().toString();
        return generateAvatarForString(key, size);
    }

    @Override
    public UserAvatarDto generateAvatarForString(@NotNull String key, int size) {
        final byte[] avatarByKey = avatarGeneratorClient.getAvatarByKey(key, size);
        final byte[] encode = Base64.getEncoder().encode(avatarByKey);
        return new UserAvatarDto(AvatarDataType.BASE64, UsersConstants.BASE_IMAGE_PREFIX + new String(encode));
    }
}
