package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.DateOperations;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class DurationArithmeticExpression implements PreEvaluatedArgumentsExpression {
  public static final DurationArithmeticExpression DURATION_BETWEEN = new DurationArithmeticExpression("duration_between");

  private final String key;

  public DurationArithmeticExpression(String key) {
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

    if (arguments.size() != 2) {
      throw new JsonLogicEvaluationException("duration arithmetic requires 2 arguments");
    }

    /*
     * --- Supported Operations ---
     * DURATION_BETWEEN ("duration_between")
     *    1. [Date, Date]
     *
     * NOTE: Return type is string representation of the duration
     */

    try {
        OffsetDateTime start = DateOperations.fromDateString((String) arguments.get(0));
        OffsetDateTime end = DateOperations.fromDateString((String) arguments.get(1));
        return Duration.between(start, end).toString();
    } catch (Exception e) {
      return null;
    }
  }
}
