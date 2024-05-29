package io.github.jamsesso.jsonlogic;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

public class DurationArithmeticExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    public void durationBetweenTwoInvalidDates() throws JsonLogicException {
        OffsetDateTime from = OffsetDateTime.parse("2024-05-28T00:00Z");
        OffsetDateTime until = from.plusDays(100L);
        assertEquals(
                null,
                jsonLogic.apply("{\"duration.between\": [\"2024-05-28T\", \"" + until + "\"]}", null)
        );
    }

    @Test
    public void durationBetweenTwoDates() throws JsonLogicException {
        OffsetDateTime from = OffsetDateTime.now();
        OffsetDateTime until = from.plusDays(100L);
        Duration duration = Duration.ofDays(100L);
        assertEquals(
                duration.toString(),
                jsonLogic.apply("{\"duration.between\": [\"" + from + "\", \"" + until + "\"]}", null)
        );
    }

    @Test
    public void durationOf() throws JsonLogicException {
        Duration duration = Duration.ofSeconds(100L);
        assertEquals(
                duration.toString(),
                jsonLogic.apply("{\"duration.of\": [100, \"SECONDS\"]}", null)
        );
    }

    @Test
    public void durationOfWithIntervalAsString() throws JsonLogicException {
        Duration duration = Duration.ofSeconds(100L);
        assertEquals(
                duration.toString(),
                jsonLogic.apply("{\"duration.of\": [\"100\", \"SECONDS\"]}", null)
        );
    }

    @Test
    public void durationAs() throws JsonLogicException {
        Duration duration = Duration.ofSeconds(100L);
        assertEquals(
                new BigDecimal(100L),
                jsonLogic.apply("{\"duration.as\": [\"" + duration + "\", \"SECONDS\"]}", null)
        );
    }

    @Test
    public void dateAddWithDurationOf() throws JsonLogicException {
        OffsetDateTime from = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        assertEquals(
                LocalDate.now().atStartOfDay().plusDays(100L).atOffset(ZoneOffset.UTC).toString(),
                jsonLogic.apply("{\"date.add\": [\"" + from + "\", {\"duration.of\": [\"100\", \"DAYS\"]}]}", null)
        );
    }

    @Test
    public void durationAsWithDurationOf() throws JsonLogicException {
        assertEquals(
                new BigDecimal(100L),
                jsonLogic.apply("{\"duration.as\": [{\"duration.of\": [\"100\", \"DAYS\"]}, \"DAYS\"]}", null)
        );
    }

    @Test
    public void durationOfWithDurationAs() throws JsonLogicException {
        assertEquals(
                new BigDecimal(Duration.ofDays(100L).toSeconds()),
                jsonLogic.apply("{\"duration.as\": [{\"duration.of\": [\"100\", \"DAYS\"]}, \"SECONDS\"]}", null)
        );
    }
}
