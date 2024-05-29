package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.BigDecimalOperations;
import io.github.jamsesso.jsonlogic.utils.DateOperations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class DurationArithmeticExpression implements PreEvaluatedArgumentsExpression {
  public static final DurationArithmeticExpression DURATION_OF = new DurationArithmeticExpression("duration.of");
  public static final DurationArithmeticExpression DURATION_BETWEEN = new DurationArithmeticExpression("duration.between");
  public static final DurationArithmeticExpression DURATION_AS = new DurationArithmeticExpression("duration.as");

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
     * DURATION_OF ("duration.of")
     *    1. [intervalToAdd, intervalUnit]
     * DURATION_BETWEEN ("duration.between")
     *    1. [Date, Date]
     *
     * RETURNS: Duration as String
     *
     * DURATION_AS ("duration.as")
     *    1. [Duration, intervalUnit]
     *
     * RETURNS: interval as NUMBER (BigDecimal)
     */

    try {
      if (Objects.equals(key, "duration.between")) {
        OffsetDateTime start = DateOperations.fromDateString((String) arguments.get(0));
        OffsetDateTime end = DateOperations.fromDateString((String) arguments.get(1));
        return Duration.between(start, end).toString();
      } else if (Objects.equals(key, "duration.of")) {
        BigDecimal intervalToAdd;
        if (arguments.get(0) instanceof Number) {
          intervalToAdd = BigDecimalOperations.fromNumber((Number) arguments.get(0));
        } else {
          String intervalToAddString = (String) arguments.get(0);
          if (intervalToAddString.trim().isEmpty()) {
            intervalToAddString = "0";
          }
          intervalToAdd = new BigDecimal(intervalToAddString);
        }
        ChronoUnit intervalUnit = ChronoUnit.valueOf((String) arguments.get(1));
        return Duration.of(Long.parseLong(intervalToAdd.toString()), intervalUnit).toString();
      } else {
        Duration duration = Duration.parse((String) arguments.get(0));
        ChronoUnit intervalUnit = ChronoUnit.valueOf((String) arguments.get(1));
        switch (intervalUnit) {
          case MILLIS:
            return BigDecimal.valueOf(duration.toMillis());
          case SECONDS:
            return BigDecimal.valueOf(duration.toSeconds());
          case MINUTES:
            return BigDecimal.valueOf(duration.toMinutes());
          case HOURS:
            return BigDecimal.valueOf(duration.toHours());
          case DAYS:
            return BigDecimal.valueOf(duration.toDays());
          default:
            return null;
        }
      }
    } catch (Exception e) {
      return null;
    }
  }
}
