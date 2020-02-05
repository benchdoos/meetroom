package com.github.benchdoos.meetroom.utils;

import com.github.benchdoos.meetroom.domain.DateRange;

import java.time.DayOfWeek;
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
    public static ZonedDateTime truncateTimeToDayStart(ZonedDateTime dateTime) {
        return dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static ZonedDateTime truncateTimeToDayEnd(ZonedDateTime dateTime) {
        return dateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    /**
     * Sets seconds and milliseconds of given dateTime to 00.000.
     *
     * @param dateTime to truncate
     * @return truncated dateTime
     */
    public static ZonedDateTime truncateSecondsToStart(ZonedDateTime dateTime) {
        return dateTime.withSecond(0).withNano(0);
    }

    public static ZonedDateTime truncateSecondsToEnd(ZonedDateTime dateTime) {
        return dateTime.withSecond(59).withNano(999999999);
    }

    /**
     * Returns monday - sunday date range of given day
     *
     * @param now day
     * @return range from monday - sunday
     */
    public static DateRange getWeekRange(ZonedDateTime now) {
        final DayOfWeek day = now.getDayOfWeek();

        final ZonedDateTime mondayDay = now.with(DayOfWeek.MONDAY);
        final ZonedDateTime sundayDay = now.with(DayOfWeek.SUNDAY);

        return new DateRange(
                DateUtils.truncateTimeToDayStart(mondayDay),
                DateUtils.truncateTimeToDayEnd(sundayDay));
    }
}
