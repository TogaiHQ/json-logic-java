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
    public void addSeconds() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToAdd = 1L;
        assertEquals(
                date.plusSeconds(intervalToAdd),
                jsonLogic.apply("{\"date_add\": [\"" + date + "\", 1, \"SECONDS\"]}", null)
        );
    }

    @Test
    public void addMinutes() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToAdd = 1L;
        assertEquals(
                date.plusMinutes(intervalToAdd),
                jsonLogic.apply("{\"date_add\": [\"" + date + "\", 1, \"MINUTES\"]}", null)
        );
    }

    @Test
    public void addDays() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToAdd = 1L;
        assertEquals(
                date.plusDays(intervalToAdd),
                jsonLogic.apply("{\"date_add\": [\"" + date + "\", 1, \"DAYS\"]}", null)
        );
    }

    @Test
    public void addMonths() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToAdd = 1L;
        assertEquals(
                date.plusMonths(intervalToAdd),
                jsonLogic.apply("{\"date_add\": [\"" + date + "\", 1, \"MONTHS\"]}", null)
        );
    }

    @Test
    public void addYears() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToAdd = 1L;
        assertEquals(
                date.plusYears(intervalToAdd),
                jsonLogic.apply("{\"date_add\": [\"" + date + "\", 1, \"YEARS\"]}", null)
        );
    }

    @Test
    public void subtractSeconds() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToSubtract = 1L;
        assertEquals(
                date.minusSeconds(intervalToSubtract),
                jsonLogic.apply("{\"date_sub\": [\"" + date + "\", 1, \"SECONDS\"]}", null)
        );
    }

    @Test
    public void subtractMinutes() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToSubtract = 1L;
        assertEquals(
                date.minusMinutes(intervalToSubtract),
                jsonLogic.apply("{\"date_sub\": [\"" + date + "\", 1, \"MINUTES\"]}", null)
        );
    }

    @Test
    public void subtractDays() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToSubtract = 1L;
        assertEquals(
                date.minusDays(intervalToSubtract),
                jsonLogic.apply("{\"date_sub\": [\"" + date + "\", 1, \"DAYS\"]}", null)
        );
    }

    @Test
    public void subtractMonths() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToSubtract = 1L;
        assertEquals(
                date.minusMonths(intervalToSubtract),
                jsonLogic.apply("{\"date_sub\": [\"" + date + "\", 1, \"MONTHS\"]}", null)
        );
    }

    @Test
    public void subtractYears() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        long intervalToSubtract = 1L;
        assertEquals(
                date.minusYears(intervalToSubtract),
                jsonLogic.apply("{\"date_sub\": [\"" + date + "\", 1, \"YEARS\"]}", null)
        );
    }

    @Test
    public void addDurationToDate() throws JsonLogicException {
        OffsetDateTime from = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        Duration duration = Duration.ofDays(100L);
        assertEquals(
                LocalDate.now().atStartOfDay().plusDays(100L).atOffset(ZoneOffset.UTC).toString(),
                jsonLogic.apply("{\"date_add\": [\"" + from + "\", \"" + duration + "\"]}", null)
        );
    }

//    Invalid Number of arguments throws JsonLogicEvaluationException
    @Test
    public void invalidNumberOfArguments() {
        try {
            jsonLogic.apply("{\"date_add\": [\"2024-05-28T00:00:00Z\"]}", null);
        } catch (JsonLogicException e) {
            assertTrue(e instanceof JsonLogicEvaluationException);
            assertEquals(e.getMessage(), "date arithmetic requires 2 or 3 arguments");
        }
    }

//    Invalid Data Type of Arguments returns null
    @Test
    public void invalidDataTypeForArgument() throws JsonLogicException {
        OffsetDateTime date = OffsetDateTime.now();
        assertNull(jsonLogic.apply("{\"date_add\": [\"" + date + "\", \"1\", \"SECONDS\"]}", null));
    }
}
