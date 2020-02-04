package com.github.benchdoos.meetroom.utils;

import java.time.ZonedDateTime;

/**
 * Date utilities
 */
public class DateUtils {
    /**
     * Sets time of dateTime to 0:00.00.000
     *
     * @param dateTime
     * @return
     */
    public static ZonedDateTime truncateTime(ZonedDateTime dateTime) {
        return dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * Sets seconds and milliseconds of given dateTime to 00.000.
     *
     * @param dateTime to truncate
     * @return truncated dateTime
     */
    public static ZonedDateTime truncateSeconds(ZonedDateTime dateTime) {
        return dateTime.withSecond(0).withNano(0);
    }
}
