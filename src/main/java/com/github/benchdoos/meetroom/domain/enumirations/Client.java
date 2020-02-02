package com.github.benchdoos.meetroom.domain.enumirations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Clients list. It's scope names.
 * Provided if this api will be used from other places.
 */
@RequiredArgsConstructor
@Getter
public enum Client {
    WEB_APP("web");

    private final String scope;
}
