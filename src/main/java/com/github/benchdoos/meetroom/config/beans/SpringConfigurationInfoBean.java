package com.github.benchdoos.meetroom.config.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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

    private final Environment environment;

    /**
     * Get public application url. Can be called from request. Otherwise it will throw exception.
     *
     * @return full public application url
     * @throws IllegalStateException if method called not from request
     */
    public String getPublicFullApplicationUrl() {
        final String prefix = servletRequest.isSecure() ? "https://" : "http://";

        final String fullApplicationPath = prefix
                + InetAddress.getLoopbackAddress().getCanonicalHostName()
                + ":"
                + servletRequest.getLocalPort()
                + servletContext.getContextPath();
        log.debug("Full application path: {}", fullApplicationPath);
        return fullApplicationPath;
    }

}
