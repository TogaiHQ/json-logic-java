package io.github.jamsesso.jsonlogic.evaluator.expressions;

import io.github.jamsesso.jsonlogic.evaluator.JsonLogicEvaluationException;

import java.util.List;

public class StringContainsExpression implements PreEvaluatedArgumentsExpression {
    public static final StringContainsExpression INSTANCE = new StringContainsExpression();

    private StringContainsExpression() {
        // Only one instance can be constructed. Use StringContainsExpression.INSTANCE
    }

    @Override
    public String key() {
        return "contains";
    }

    @Override
    public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
        if (arguments.isEmpty()) {
            return null;
        }

        if (arguments.size() != 2) {
            throw new JsonLogicEvaluationException("contains operator requires 2 arguments");
        }

        Object argument = arguments.get(0);
        Object charSequence = arguments.get(1);

        if (argument == null || charSequence == null) {
            return null;
        }

        try {
            /**
             * Using String.valueOf() to supported input arguments of any type
             * Return type is boolean
             */
            return String.valueOf(argument).contains(String.valueOf(charSequence));
        } catch (Exception e) {
            return null;
        }
    }
}
