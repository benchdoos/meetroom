package com.github.benchdoos.meetroom.domain.interfaces;

import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;

/**
 * Unites all info about avatar
 */
public interface AvatarInfo {
    AvatarDataType getType();

    String getData();
}
