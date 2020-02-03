package com.github.benchdoos.meetroom.utils;

import java.time.ZonedDateTime;

public class DateUtils {
    public static ZonedDateTime truncateTime(ZonedDateTime dateTime) {
        return dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }
}
