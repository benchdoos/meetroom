package com.github.benchdoos.meetroom.utils;

import com.github.benchdoos.meetroom.domain.DateRange;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Date utilities
 */
public class DateUtils {
    /**
     * Sets hours, minutes, seconds and milliseconds of given dateTime to 0:00.00.000
     *
     * @param dateTime date to change time
     * @return time with changed
     */
    public static ZonedDateTime truncateTimeToDayStart(ZonedDateTime dateTime) {
        return dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
    /**
     * Sets hours, minutes, seconds and milliseconds of given dateTime to 0:00.00.999999999
     *
     * @param dateTime date to change time
     * @return time with changed
     */
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
    /**
     * Sets seconds and milliseconds of given dateTime to 59.99999.
     *
     * @param dateTime to truncate
     * @return truncated dateTime
     */
    public static ZonedDateTime truncateSecondsToEnd(ZonedDateTime dateTime) {
        return dateTime.withSecond(59).withNano(99999); //prevents from saving plus 1 minute
    }

    /**
     * Convert {@link Date} to {@link ZonedDateTime} with system default {@link ZoneId}
     *
     * @param date to convert
     * @return formated
     */
    public static ZonedDateTime toZoneDateTime(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
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

    /**
     * Creates {@link DateRange} from two given times, truncating their seconds to start and end
     *
     * @param fromDate from date
     * @param toDate to date
     * @return date range within two dates
     * @see #truncateSecondsToStart(ZonedDateTime)
     * @see #truncateSecondsToEnd(ZonedDateTime)
     */
    public static DateRange createDateRange(ZonedDateTime fromDate, ZonedDateTime toDate) {
        return new DateRange(
                truncateSecondsToStart(fromDate),
                truncateSecondsToEnd(toDate)
        );
    }

    /**
     * Gives date range duration in minutes. First minute is counted as full.
     * <pre>
     *      Example:
     *      09:00, 09:30 -> 31
     *      09:30, 09:59 -> 30
     *      10:00, 10:28 -> 29
     * </pre>
     *
     * @param firstDate date
     * @param secondDate date
     * @return duration in minutes
     */
    public static long getDateRangeDuration(ZonedDateTime firstDate, ZonedDateTime secondDate) {
        final ChronoUnit chronoUnit = ChronoUnit.MINUTES;
        final long between = chronoUnit.between(firstDate, secondDate);

        //This prevents incorrect duration counting. Ex: 09:00 -> 09:29 duration is 29 minutes.
        //But physically toDate has 59 sec, so actually we can accept that duration is 30 minutes.
        return between + 1;
    }
}
