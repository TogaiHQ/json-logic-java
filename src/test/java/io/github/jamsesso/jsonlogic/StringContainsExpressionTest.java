package io.github.jamsesso.jsonlogic;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

public class StringContainsExpressionTest {
    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    public void stringStringContains() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"contains\": [\"jsonLogic\", \"Logic\"]}", null));
    }

    @Test
    public void numberNumberContains() throws JsonLogicException {
        assertEquals(
                true,
                jsonLogic.apply("{\"contains\": [" + BigDecimal.valueOf(589) + ", " + BigDecimal.valueOf(8) + "]}", null)
        );
    }

    @Test
    public void stringNumberContains() throws JsonLogicException {
        assertEquals(
                true,
                jsonLogic.apply("{\"contains\": [\"SUCCESS_200\", " + BigDecimal.valueOf(2) + "]}", null)
        );
    }

    @Test
    public void stringBooleanContains() throws JsonLogicException {
        assertEquals(
                true,
                jsonLogic.apply("{\"contains\": [\"SUCCESS = true\", " + true + "]}", null)
        );
    }

    @Test
    public void dateStringContains() throws JsonLogicException {
        assertEquals(
                true,
                jsonLogic.apply("{\"contains\": [{\"date\": [\"2024-05-28T00:00:00Z\"]}, \"28\"]}", null)
        );
    }

    @Test
    public void dateNumberContains() throws JsonLogicException {
        assertEquals(
                true,
                jsonLogic.apply("{\"contains\": [{\"date\": [\"2024-05-28T00:00:00Z\"]}, " + BigDecimal.valueOf(28) + "]}", null)
        );
    }

    @Test
    public void stringNullContains() throws JsonLogicException {
        assertEquals(
                null,
                jsonLogic.apply("{\"contains\": [\"null checks\", " + null + "]}", null)
        );
    }

    @Test
    public void nullNullContains() throws JsonLogicException {
        assertEquals(
                null,
                jsonLogic.apply("{\"contains\": [" + null + ", " + null + "]}", null)
        );
    }

    @Test
    public void nullStringContains() throws JsonLogicException {
        assertEquals(
                null,
                jsonLogic.apply("{\"contains\": [" + null + ", \"ul\"]}", null)
        );
    }
}
