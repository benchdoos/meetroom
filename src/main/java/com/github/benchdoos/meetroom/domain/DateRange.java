package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * Range of two dates
 */
@AllArgsConstructor
@Getter
@ToString
public class DateRange {
    /**
     * Start of date range
     */
    private ZonedDateTime fromDate;

    /**
     * End of date range
     */
    private ZonedDateTime toDate;
}
