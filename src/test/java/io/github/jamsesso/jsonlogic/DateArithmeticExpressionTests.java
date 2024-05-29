package io.github.jamsesso.jsonlogic;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

public class DateArithmeticExpressionTests {
    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    public void addDurationToDate() throws JsonLogicException {
        OffsetDateTime from = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        Duration duration = Duration.ofDays(100L);
        assertEquals(
                LocalDate.now().atStartOfDay().plusDays(100L).atOffset(ZoneOffset.UTC).toString(),
                jsonLogic.apply("{\"date.add\": [\"" + from + "\", \"" + duration + "\"]}", null)
        );
    }

    @Test
    public void subtractDurationToDate() throws JsonLogicException {
        OffsetDateTime from = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        Duration duration = Duration.ofDays(100L);
        assertEquals(
                LocalDate.now().atStartOfDay().minusDays(100L).atOffset(ZoneOffset.UTC).toString(),
                jsonLogic.apply("{\"date.sub\": [\"" + from + "\", \"" + duration + "\"]}", null)
        );
    }

//    Invalid Number of arguments throws JsonLogicEvaluationException
    @Test
    public void invalidNumberOfArguments() {
        try {
            jsonLogic.apply("{\"date.add\": [\"2024-05-28T00:00:00Z\"]}", null);
        } catch (JsonLogicException e) {
            assertTrue(e instanceof JsonLogicEvaluationException);
            assertEquals(e.getMessage(), "date arithmetic requires 2 arguments");
        }
    }

//    Invalid Data Type of Arguments returns null
    @Test
    public void invalidDateString() throws JsonLogicException {
        Duration duration = Duration.ofDays(100L);
        assertNull(jsonLogic.apply("{\"date.add\": [\"2024-05-28T\", \"" + duration + "\"]}", null));
    }

    @Test
    public void invalidDurationString() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        assertNull(jsonLogic.apply("{\"date.add\": [\"" + date + "\", \"P1111\"]}", null));
    }
}
