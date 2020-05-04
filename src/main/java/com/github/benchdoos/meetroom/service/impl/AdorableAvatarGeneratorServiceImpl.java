package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.client.AvatarGeneratorClient;
import com.github.benchdoos.meetroom.config.constants.UsersConstants;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdorableAvatarGeneratorServiceImpl implements AvatarGeneratorService {
    private final AvatarGeneratorClient avatarGeneratorClient;

    @Override
    public String generateRandomAvatar(@Size(min = 40, max = 256) int size) {
        final String key = UUID.randomUUID().toString();
        return generateAvatarForString(key, size);
    }

    @Override
    public String generateAvatarForString(@NotNull String key, @Size(min = 40, max = 256) int size) {
        final byte[] avatarByKey = avatarGeneratorClient.getAvatarByKey(key, size);
        final byte[] encode = Base64.getEncoder().encode(avatarByKey);
        return UsersConstants.BASE_IMAGE_PREFIX + new String(encode);
    }
}
