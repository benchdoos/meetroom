package com.github.benchdoos.meetroom.domain;

import com.github.benchdoos.meetroom.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@ToString
public class DateRange {
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;

    /**
     * Checks if data is valid
     *
     * @return true if valid
     */
    public boolean isValid() {

        if (fromDate != null && toDate != null) {
            final ZonedDateTime from = DateUtils.truncateSeconds(fromDate);
            final ZonedDateTime to = DateUtils.truncateSeconds(toDate);

            return from.plusDays(7).isBefore(to);
        }

        return false;
    }
}
