package com.github.benchdoos.meetroom.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/***
 * Api security properties.
 * Contain token validity and other needed stuff
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("api-security")
public class ApiSecurityProperties {

    @NotBlank
    private String secret;

    @Min(60)
    private long tokenValidity;

    @Min(60)
    private long refreshTokenValidity;
}
