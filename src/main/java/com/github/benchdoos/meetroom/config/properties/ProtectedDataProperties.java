package com.github.benchdoos.meetroom.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/***
 * Protected roles that can not be deleted or updated by anyone except system
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("protected")
public class ProtectedDataProperties {

    private List<String> roles = Collections.emptyList();

}
