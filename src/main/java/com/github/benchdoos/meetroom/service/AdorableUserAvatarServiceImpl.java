package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.client.AvatarClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Base64;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdorableUserAvatarServiceImpl implements UserAvatarService {

    private final AvatarClient avatarClient;

    @Override
    public String generateRandomAvatar(@Size(min = 40, max = 256) int size) {
        final String key = UUID.randomUUID().toString();

        final byte[] avatarByKey = avatarClient.getAvatarByKey(key, size);

        final byte[] encode = Base64.getEncoder().encode(avatarByKey);

        return new String(encode);
    }

    @Override
    public String generateAvatarForString(@NotNull String key, @Size(min = 40, max = 256) int size) {
        return null;
    }
}
