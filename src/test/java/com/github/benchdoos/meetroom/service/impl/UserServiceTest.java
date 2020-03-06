package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.abstracts.AbstractIntegrationCommonTest;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest extends AbstractIntegrationCommonTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
//        Mockito.when(userRepository.findByUsername()).thenReturn(alex);
    }

    @org.junit.Test
    public void findUser() {
        final List<User> all = userRepository.findAll();
        System.out.println(all.size());
    }
}