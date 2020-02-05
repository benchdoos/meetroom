package com.github.benchdoos.meetroom.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface IndexViewService {

    String getAllRooms(@PageableDefault Pageable pageable, Model model);

    String getAllAvailable(@PageableDefault Pageable pageable, Model model);

    String getMeetingRoomById(@PathVariable UUID uuid, @PageableDefault Pageable pageable, Model model);
}
