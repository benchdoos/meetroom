package com.github.benchdoos.meetroom.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Properties for meeting rooms
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("meetroom")
//todo here we can add custom annotation to validate that minimumReservationValue is >= maximumReservationValue using reflection
public class MeetroomProperties {
    /**
     * Minimum meet room reservation value in minutes
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 10089) //7 days - 1 minute
    private Integer minimumReservationValue;

    /**
     * Maximum meet room reservation value in minutes
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 10080) //7 days
    private Integer maximumReservationValue;
}
