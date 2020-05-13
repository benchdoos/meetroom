package com.github.benchdoos.meetroom.config.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import java.net.InetAddress;

/**
 * Component to get {@link ServletContext} from spring bean processor
 */
@Slf4j
@RequiredArgsConstructor
@Getter
@Component
public class SpringConfigurationInfoBean {
    private final ServletContext servletContext;
    private final ServletRequest servletRequest;

    @PostConstruct
    private void afterCreate() {
        System.out.println("HELLO FROM POST CONSTRACT!");
        System.out.println("------------------------------");
        System.out.println("canonicalHostName: " + InetAddress.getLoopbackAddress().getCanonicalHostName());
        System.out.println("hostName: " + InetAddress.getLoopbackAddress().getHostName());
        System.out.println("hostAddress: " + InetAddress.getLoopbackAddress().getHostAddress());
        System.out.println("------------------------------");
        System.out.println("Secure: " + servletRequest.isSecure());
        System.out.println("localPort: " + servletRequest.getLocalPort());
        System.out.println("contextPath: " + servletContext.getContextPath());
        System.out.println("------------------------------");
        System.out.println("getPublicFullApplicationUrl: " + getPublicFullApplicationUrl());
        System.out.println("------------------------------");
    }

    public String getPublicFullApplicationUrl() {
        final String prefix = servletRequest.isSecure() ? "https://" : "http://";

        final String fullApplicationPath = prefix
                + InetAddress.getLoopbackAddress().getHostAddress()
                + ":"
                + servletRequest.getLocalPort()
                + servletContext.getContextPath();
        log.debug("Full application path: {}", fullApplicationPath);
        return fullApplicationPath;
    }
}
