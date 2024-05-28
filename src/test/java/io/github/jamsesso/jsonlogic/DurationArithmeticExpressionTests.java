package io.github.jamsesso.jsonlogic;

import org.junit.Test;

import java.time.Duration;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

public class DurationArithmeticExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    public void durationBetweenTwoInvalidDates() throws JsonLogicException {
        OffsetDateTime from = OffsetDateTime.parse("2024-05-28T00:00Z");
        OffsetDateTime until = from.plusDays(100L);
        Duration duration = Duration.ofDays(100L);
        assertEquals(
                null,
                jsonLogic.apply("{\"duration_between\": [\"2024-05-28T\", \"" + until + "\"]}", null)
        );
    }

    @Test
    public void durationBetweenTwoDates() throws JsonLogicException {
        OffsetDateTime from = OffsetDateTime.now();
        OffsetDateTime until = from.plusDays(100L);
        Duration duration = Duration.ofDays(100L);
        assertEquals(
                duration.toString(),
                jsonLogic.apply("{\"duration_between\": [\"" + from + "\", \"" + until + "\"]}", null)
        );
    }
}
