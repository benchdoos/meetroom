package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.client.AvatarGeneratorClient;
import com.github.benchdoos.meetroom.config.constants.UsersConstants;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.validation.constraints.NotNull;
import java.util.UUID;


/**
 * Adorable avatar generation
 *
 * @see <a href="adorable.io">adorable.io</a>
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AdorableAvatarGeneratorServiceImpl implements AvatarGeneratorService {
    private static final int COMPRESSION_LEVEL = 4;
    private final AvatarGeneratorClient avatarGeneratorClient;

    @Override
    public UserAvatarDto generateRandomAvatar(int size) {
        final String key = UUID.randomUUID().toString();
        return generateAvatarForString(key, size);
    }

    @Override
    public UserAvatarDto generateAvatarForString(@NotNull String key, int size) {
        final byte[] avatarByKey = avatarGeneratorClient.getAvatarByKey(key, size);
        final String encode = Base64Utils.encodeToString(avatarByKey); //todo fix 3a issue
        return new UserAvatarDto(AvatarDataType.BASE64, UsersConstants.BASE64_PNG_IMAGE_PREFIX + encode);
    }
}
