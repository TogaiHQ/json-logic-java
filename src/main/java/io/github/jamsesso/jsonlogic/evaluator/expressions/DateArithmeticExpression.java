package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.DateOperations;

import javax.xml.datatype.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class DateArithmeticExpression implements PreEvaluatedArgumentsExpression {
  public static final DateArithmeticExpression DATE_ADD = new DateArithmeticExpression("date.add");
  public static final DateArithmeticExpression DATE_SUBTRACT = new DateArithmeticExpression("date.sub");

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
     * DATE_ADD("date.add"):
     *    1. [date, duration ISO8601]
     * DATE_SUB("date.sub"):
     *    1. [date, duration ISO8601]
     *
     * RETURNS: OffsetDateTime as String
     */

    if (arguments.size() != 2) {
      throw new JsonLogicEvaluationException("date arithmetic requires 2 arguments");
    }

    try {
      OffsetDateTime date = DateOperations.fromDateString((String) arguments.get(0));
      Duration duration = DateOperations.fromDurationString((String) arguments.get(1));

      return Objects.equals(key, "date.add")
              ? DateOperations.addDurationToDate(date, duration).toString()
              : DateOperations.subtractDurationToDate(date, duration).toString();
    } catch (Exception e) {
      return null;
    }
  }
}
