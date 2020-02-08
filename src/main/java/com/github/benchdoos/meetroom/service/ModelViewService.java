package com.github.benchdoos.meetroom.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface ModelViewService {

    String getAllRooms(@PageableDefault Pageable pageable, Model model);

    String getAllAvailable(@PageableDefault Pageable pageable, Model model);

    String getMeetingRoomById(@PathVariable UUID uuid, @PageableDefault Pageable pageable, Model model);

    String getMeetingRoomById(@PathVariable UUID uuid, ZonedDateTime fromDate, @PageableDefault Pageable pageable, Model model);

    String getMeetingEventInfoById(@PathVariable UUID id, Model model);
}