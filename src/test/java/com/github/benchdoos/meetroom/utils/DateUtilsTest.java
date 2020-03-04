package com.github.benchdoos.meetroom.utils;

import com.github.benchdoos.meetroom.abstracts.AbstractUnitTest;
import com.github.benchdoos.meetroom.domain.DateRange;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest extends AbstractUnitTest {

    @Test
    void truncateTimeToDayStartMustCorrectlyTruncateTime() {
        final ZonedDateTime testDateTime = easyRandom.nextObject(ZonedDateTime.class);
        final ZonedDateTime truncateNow = DateUtils.truncateTimeToDayStart(testDateTime);
        assertDateDayEquals(testDateTime, truncateNow);
        assertThat(truncateNow).isBefore(testDateTime);
        assertThat(truncateNow.getHour()).isEqualTo(0);
        assertThat(truncateNow.getMinute()).isEqualTo(0);
        assertThat(truncateNow.getSecond()).isEqualTo(0);
    }

    @Test
    void truncateTimeToDayEndMustCorrectlyTruncateTime() {
        final ZonedDateTime testDateTime = easyRandom.nextObject(ZonedDateTime.class);
        final ZonedDateTime truncateNow = DateUtils.truncateTimeToDayEnd(testDateTime);
        assertDateDayEquals(testDateTime, truncateNow);
        assertThat(truncateNow).isAfter(testDateTime);
        assertThat(truncateNow.getHour()).isEqualTo(23);
        assertThat(truncateNow.getMinute()).isEqualTo(59);
        assertThat(truncateNow.getSecond()).isEqualTo(59);
    }

    @Test
    void truncateSecondsToStartMustCorrectlyTruncateSeconds() {
        final ZonedDateTime testDateTime = easyRandom.nextObject(ZonedDateTime.class);
        final ZonedDateTime truncateNow = DateUtils.truncateSecondsToStart(testDateTime);
        assertDateDayEquals(testDateTime, truncateNow);
        assertThat(truncateNow.getHour()).isEqualTo(testDateTime.getHour());
        assertThat(truncateNow.getSecond()).isEqualTo(0);
        assertThat(truncateNow.getNano()).isEqualTo(0);
    }

    @Test
    void truncateSecondsToEndMustCorrectlyTruncateSeconds() {
        final ZonedDateTime testDateTime = easyRandom.nextObject(ZonedDateTime.class);
        final ZonedDateTime truncateNow = DateUtils.truncateSecondsToEnd(testDateTime);
        assertDateDayEquals(testDateTime, truncateNow);
        assertThat(truncateNow.getHour()).isEqualTo(testDateTime.getHour());
        assertThat(truncateNow.getSecond()).isEqualTo(59);
    }

    @Test
    void getWeekRangeMustCreateCorrectDateRange() {
        final ZonedDateTime testDateTime = easyRandom.nextObject(ZonedDateTime.class);
        final DateRange weekRange = DateUtils.getWeekRange(testDateTime);

        assertThat(weekRange.getFromDate().getDayOfWeek()).isEqualByComparingTo(DayOfWeek.MONDAY);
        assertThat(weekRange.getToDate().getDayOfWeek()).isEqualByComparingTo(DayOfWeek.SUNDAY);

        assertThat(weekRange.getFromDate()).isEqualTo(DateUtils.truncateTimeToDayStart(weekRange.getFromDate()));
        assertThat(weekRange.getToDate()).isEqualTo(DateUtils.truncateTimeToDayEnd(weekRange.getToDate()));
    }

    /**
     * @see DateUtils#getDateRangeDuration(ZonedDateTime, ZonedDateTime) why 1441 instead of 1440
     */
    @Test
    void getDateRangeDurationForDay() {
        final ZonedDateTime fromDate = easyRandom.nextObject(ZonedDateTime.class);
        final ZonedDateTime toDate = fromDate.plusDays(1);

        final long dateRangeDuration = DateUtils.getDateRangeDuration(fromDate, toDate);

        final int hoursInDay = 24 * 60;
        assertThat(dateRangeDuration).isEqualTo(hoursInDay + 1);
    }

    /**
     * @see DateUtils#getDateRangeDuration(ZonedDateTime, ZonedDateTime) why ‭10081‬ instead of ‭10080‬
     */
    @Test
    void getDateRangeDurationForWeek() {
        final ZonedDateTime fromDate = easyRandom.nextObject(ZonedDateTime.class);
        final ZonedDateTime toDate = fromDate.plusWeeks(1);

        final long dateRangeDuration = DateUtils.getDateRangeDuration(fromDate, toDate);

        final int hoursInWeek = 7 * 24 * 60;
        assertThat(dateRangeDuration).isEqualTo(hoursInWeek + 1);
    }

    private void assertDateDayEquals(ZonedDateTime now, ZonedDateTime truncateNow) {
        assertThat(truncateNow).isNotNull();
        assertThat(truncateNow.getYear()).isEqualTo(now.getYear());
        assertThat(truncateNow.getMonth()).isEqualTo(now.getMonth());
        assertThat(truncateNow.getDayOfMonth()).isEqualTo(now.getDayOfMonth());
    }
}