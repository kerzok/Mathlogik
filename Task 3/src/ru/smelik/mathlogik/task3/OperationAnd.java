package ru.smelik.mathlogik.task3;

/**
 * Created by Smelik Nick.
 */
public class OperationAnd extends BinaryOperation {

    public OperationAnd(Expression leftToken, Expression rightToken) {
        super(leftToken, rightToken);
    }

    @Override
    protected boolean calculate(boolean leftToken, boolean rightToken) {
        return leftToken & rightToken;
    }

    @Override
    public String toString() {
        String left = leftToken.toString();
        String right = rightToken.toString();

        if (leftToken instanceof OperationImplication ||
                leftToken instanceof OperationAnd ||
                leftToken instanceof OperationOr)
            left = "(" + left + ")";
        if (rightToken instanceof OperationImplication ||
                rightToken instanceof OperationAnd ||
                rightToken instanceof OperationOr)
            right = "(" + right + ")";

        return left + "&" + right;
    }

    @Override
    public boolean equals(Expression expression) {
        if (expression instanceof OperationAnd) {
            return (this.getLeftToken().equals(((OperationAnd) expression).getLeftToken()) && this.getRightToken().equals(((OperationAnd) expression).getRightToken()));
        } else {
            return false;
        }
    }
}
