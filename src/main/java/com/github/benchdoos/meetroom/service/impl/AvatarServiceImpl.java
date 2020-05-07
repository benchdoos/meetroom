package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.repository.AvatarRepository;
import com.github.benchdoos.meetroom.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Default implementation of {@link AvatarService}
 */
@RequiredArgsConstructor
@Service
public class AvatarServiceImpl implements AvatarService {
    private final InternalConfiguration internalConfiguration;
    private final AvatarRepository avatarRepository;

    @Override
    public Avatar getDefaultUserAvatar() {
        @NotNull final String defaultAvatarId = internalConfiguration.getUserSettings().getDefaultAvatarId();
        return avatarRepository.findById(UUID.fromString(defaultAvatarId)).orElse(null);
    }
}
