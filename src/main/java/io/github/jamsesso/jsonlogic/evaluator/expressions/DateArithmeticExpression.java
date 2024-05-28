package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.DateOperations;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class DateArithmeticExpression implements PreEvaluatedArgumentsExpression {
  public static final DateArithmeticExpression DATE_ADD = new DateArithmeticExpression("date_add");
  public static final DateArithmeticExpression DATE_SUBTRACT = new DateArithmeticExpression("date_sub");

  private final String key;

  public DateArithmeticExpression(String key) {
    this.key = key;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
    if (arguments.isEmpty()) {
      return null;
    }

    /*
     * --- Supported Operations ---
     * DATE_ADD("date_add"):
     *    1. [date, intervalToAdd, intervalUnit]
     *    2. [date, duration ISO8601]
     * DATE_SUB("date_sub"):
     *    1. [date, intervalToSubtract, intervalUnit]
     *    2. [date, duration ISO8601]
     *
     * NOTE: Returns OffsetDateTime
     */

    try {
      OffsetDateTime date = DateOperations.fromDateString((String) arguments.get(0));
      if (arguments.size() == 3) {
        BigDecimal intervalToAdd = (BigDecimal) arguments.get(1);
        ChronoUnit interval = ChronoUnit.valueOf((String) arguments.get(2));

        return Objects.equals(key, "date_add")
                ? date.plus(Long.parseLong(intervalToAdd.toString()), interval)
                : date.minus(Long.parseLong(intervalToAdd.toString()), interval);
      } else if (arguments.size() == 2) {
        Duration duration = DateOperations.fromDurationString((String) arguments.get(1));

        return Objects.equals(key, "date_add")
                ? DateOperations.addDurationToDate(date, duration).toString()
                : DateOperations.subtractDurationToDate(date, duration).toString();
      }
    } catch (Exception e) {
      return null;
    }
    throw new JsonLogicEvaluationException("date arithmetic requires 2 or 3 arguments");
  }
}
