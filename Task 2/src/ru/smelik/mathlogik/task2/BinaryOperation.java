package ru.smelik.mathlogik.task2;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public abstract class BinaryOperation implements Expression {

    protected Expression leftToken;
    protected Expression rightToken;

    public BinaryOperation(Expression leftToken, Expression rightToken) {
        this.leftToken = leftToken;
        this.rightToken = rightToken;
    }

    Expression getLeftToken() {
        return leftToken;
    }

    Expression getRightToken() {
        return rightToken;
    }

    @Override
    public boolean evaluate(ArrayList<String> tokenList) {
        return calculate(leftToken.evaluate(tokenList), rightToken.evaluate(tokenList));
    }

    protected abstract boolean calculate(boolean leftToken, boolean rightToken);
}
