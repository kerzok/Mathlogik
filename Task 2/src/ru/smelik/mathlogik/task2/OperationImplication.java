package ru.smelik.mathlogik.task2;

/**
 * Created by Smelik Nick.
 */
public class OperationImplication extends BinaryOperation {

    public OperationImplication(Expression leftToken, Expression rightToken) {
        super(leftToken, rightToken);
    }

    @Override
    protected boolean calculate(boolean leftToken, boolean rightToken) {
        return !leftToken | rightToken;
    }

    @Override
    public String toString() {
        String left = leftToken.toString();
        String right = rightToken.toString();

        if (leftToken instanceof OperationImplication)
            left = "(" + left + ")";
        if (rightToken instanceof OperationImplication)
            right = "(" + right + ")";

        return left + "->" + right;
    }

    @Override
    public boolean equals(Expression expression) {
        if (expression instanceof OperationImplication) {
            return (getLeftToken().equals(((OperationImplication) expression).getLeftToken()) && getRightToken().equals(((OperationImplication) expression).getRightToken()));
        } else {
            return false;
        }
    }
}
