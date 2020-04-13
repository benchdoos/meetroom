package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.client.AvatarClient;
import com.github.benchdoos.meetroom.service.UserAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdorableUserAvatarServiceImpl implements UserAvatarService {

    private static final String BASE_IMAGE_PREFIX = "data:image/png;base64,";

    private final AvatarClient avatarClient;

    @Override
    public String generateRandomAvatar(@Size(min = 40, max = 256) int size) {
        final String key = UUID.randomUUID().toString();
        return generateAvatarForString(key, size);
    }

    @Override
    public String generateAvatarForString(@NotNull String key, @Size(min = 40, max = 256) int size) {
        final byte[] avatarByKey = avatarClient.getAvatarByKey(key, size);
        final byte[] encode = Base64.getEncoder().encode(avatarByKey);
        return BASE_IMAGE_PREFIX + new String(encode);
    }
}
