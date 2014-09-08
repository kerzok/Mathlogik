package ru.smelik.mathlogik.task3;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class OperationNot implements Expression {

    private Expression expression;

    public OperationNot(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        if (expression instanceof Token) {
            return "!" + expression.toString();
        } else {
            return "!(" + expression.toString() + ")";
        }
    }

    @Override
    public boolean evaluate(ArrayList<String> tokenList) {
        return !expression.evaluate(tokenList);
    }

    @Override
    public boolean equals(Expression expression) {
        if (expression instanceof OperationNot) {
            return getExpression().equals(((OperationNot) expression).getExpression());
        } else {
            return false;
        }
    }
}
