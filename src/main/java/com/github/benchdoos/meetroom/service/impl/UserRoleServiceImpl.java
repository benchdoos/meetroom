package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.repository.UserRoleRepository;
import com.github.benchdoos.meetroom.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> getAllUserRoles(Sort sort) {
        return userRoleRepository.findAll(sort);
    }

    @Override
    public Page<UserRole> findAllUserRoles(Pageable pageable) {
        return userRoleRepository.findAll(pageable);
    }
}
