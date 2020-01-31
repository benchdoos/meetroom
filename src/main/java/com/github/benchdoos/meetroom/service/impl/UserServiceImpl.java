package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserShortInfoDto;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserShortInfoDto getUserShortInfoDtoByUsername(String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        final UserShortInfoDto userShortInfoDto = new UserShortInfoDto();

        userMapper.convert(user, userShortInfoDto);

        return userShortInfoDto;
    }
}
