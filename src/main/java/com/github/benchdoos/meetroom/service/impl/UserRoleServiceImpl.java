package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.EditUserRoleDto;
import com.github.benchdoos.meetroom.exceptions.UserRoleNotFoundException;
import com.github.benchdoos.meetroom.repository.UserRoleRepository;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import com.github.benchdoos.meetroom.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final PrivilegeService privilegeService;

    @Override
    public List<UserRole> getAllUserRoles(Sort sort) {
        return userRoleRepository.findAll(sort);
    }

    @Override
    public Page<UserRole> findAllUserRoles(Pageable pageable) {
        return userRoleRepository.findAll(pageable);
    }

    @Override
    public UserRole updateUserRole(UUID id, EditUserRoleDto editUserRoleDto) {
        final UserRole roleToUpdate = userRoleRepository.findById(id).orElseThrow(UserRoleNotFoundException::new);

        final List<Privilege> privileges = privilegeService.findAllByIds(editUserRoleDto.getPrivileges());

        roleToUpdate.setName(editUserRoleDto.getName());
        roleToUpdate.setRole(editUserRoleDto.getRole());
        roleToUpdate.setPrivileges(privileges);

        return userRoleRepository.save(roleToUpdate);
    }
}
