package io.github.jamsesso.jsonlogic.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.OffsetDateTime;

public class DateOperations {
    public static OffsetDateTime fromDateString(String date) {
        return OffsetDateTime.parse(date);
    }
    public static Duration fromDurationString(String date) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newDuration(date);
    }

    public static boolean equals(OffsetDateTime a, OffsetDateTime b) {
        return a.isEqual(b);
    }

    public static OffsetDateTime addDurationToDate(OffsetDateTime date, Duration duration) {
        return date.plusYears(duration.getYears())
                .plusMonths(duration.getMonths())
                .plusDays(duration.getDays())
                .plusHours(duration.getHours())
                .plusMinutes(duration.getMinutes())
                .plusSeconds(duration.getSeconds());
    }

    public static OffsetDateTime subtractDurationToDate(OffsetDateTime date, Duration duration) {
        return date.minusYears(duration.getYears())
                .minusMonths(duration.getMonths())
                .minusDays(duration.getDays())
                .minusHours(duration.getHours())
                .minusMinutes(duration.getMinutes())
                .minusSeconds(duration.getSeconds());
    }
}
