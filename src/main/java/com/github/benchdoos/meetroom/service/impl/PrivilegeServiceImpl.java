package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.repository.PrivilegeRepository;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Override
    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll(Sort.by("name").ascending());
    }
}
