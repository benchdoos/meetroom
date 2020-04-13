package com.github.benchdoos.meetroom.config.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Slf4j
@RequiredArgsConstructor
@Getter
@Component
public class SpringConfigurationInfoBean {
    private final ServletContext servletContext;
}
