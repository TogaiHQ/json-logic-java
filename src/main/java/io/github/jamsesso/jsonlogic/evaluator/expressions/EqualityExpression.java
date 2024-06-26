package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.jamsesso.jsonlogic.utils.BigDecimalOperations;
import io.github.jamsesso.jsonlogic.utils.DateOperations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EqualityExpression implements PreEvaluatedArgumentsExpression {
  public static final EqualityExpression INSTANCE = new EqualityExpression();

  private EqualityExpression() {
    // Only one instance can be constructed. Use EqualityExpression.INSTANCE
  }

  @Override
  public String key() {
    return "==";
  }

  @Override
  public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
    if (arguments.size() != 2) {
      throw new JsonLogicEvaluationException("equality expressions expect exactly 2 arguments");
    }

    Object left = arguments.get(0);
    Object right = arguments.get(1);

    // Use the loose equality matrix
    // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Equality_comparisons_and_sameness#Loose_equality_using
    if (left == null && right == null) {
      return true;
    }

    if (left == null || right == null) {
      return false;
    }

    // Check numeric loose equality
    if (left instanceof Number && right instanceof Number) {
      return BigDecimalOperations.fromNumber((Number) left)
              .equals(BigDecimalOperations.fromNumber((Number) right));
    }

    if (left instanceof OffsetDateTime && right instanceof OffsetDateTime) {
      return DateOperations.equals((OffsetDateTime) left, (OffsetDateTime) right);
    }

    if (left instanceof OffsetDateTime && right instanceof String) {
      return compareDateToString((OffsetDateTime) left, (String) right);
    }

    if (left instanceof String && right instanceof OffsetDateTime) {
      return compareDateToString((OffsetDateTime) right, (String) left);
    }

    if (left instanceof Number && right instanceof String) {
      return compareNumberToString((Number) left, (String) right);
    }

    if (left instanceof Number && right instanceof Boolean) {
      return compareNumberToBoolean((Number) left, (Boolean) right);
    }

    // Check string loose equality
    if (left instanceof String && right instanceof String) {
      return left.equals(right);
    }

    if (left instanceof String && right instanceof Number) {
      return compareNumberToString((Number) right, (String) left);
    }

    if (left instanceof String && right instanceof Boolean) {
      return compareStringToBoolean((String) left, (Boolean) right);
    }

    // Check boolean loose equality
    if (left instanceof Boolean && right instanceof Boolean) {
      return ((Boolean) left).booleanValue() == ((Boolean) right).booleanValue();
    }

    if (left instanceof Boolean && right instanceof Number) {
      return compareNumberToBoolean((Number) right, (Boolean) left);
    }

    if (left instanceof Boolean && right instanceof String) {
      return compareStringToBoolean((String) right, (Boolean) left);
    }

    // Check non-truthy values
    return !JsonLogic.truthy(left) && !JsonLogic.truthy(right);

  }

  private boolean compareNumberToString(Number left, String right) {
    try {
      if (right.trim().isEmpty()) {
        right = "0";
      }

      return new BigDecimal(right).compareTo(BigDecimalOperations.fromNumber(left)) == 0;
    }
    catch (NumberFormatException e) {
      return false;
    }
  }

  private boolean compareNumberToBoolean(Number left, Boolean right) {
    if (right) {
      return BigDecimalOperations.fromNumber(left).equals(BigDecimal.ONE);
    }

    return BigDecimalOperations.fromNumber(left).equals(BigDecimal.ZERO);
  }

  private boolean compareStringToBoolean(String left, Boolean right) {
    return JsonLogic.truthy(left) == right;
  }

  private boolean compareDateToString(OffsetDateTime left, String right) {
    try {
      return DateOperations.equals(left, DateOperations.fromDateString(right.trim()));
    }
    catch (DateTimeParseException e) {
      return false;
    }
  }
}
