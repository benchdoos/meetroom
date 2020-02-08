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

    public String getViewableDurationString() {
        final String minimumDuration = getDurationMessage(minimumReservationValue);
        final String maximumDuration = getDurationMessage(maximumReservationValue);

        return String.format("from %s to %s", minimumDuration, maximumDuration);
    }

    private String getDurationMessage(int value) {
        if (value < 60) {
            return String.format("%s minutes", value);
        } else {
            if (value <= 1440) { //24h
                return String.format("%s hours %s minutes", value / 60, value % 60);
            } else {
                return String.format("%s days %s hours %s minutes", value / (24 * 60 * 24), value / (60 * 24), value % (60 * 24)); //todo fix
            }
        }
    }
}
