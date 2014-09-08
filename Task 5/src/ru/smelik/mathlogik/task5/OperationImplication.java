package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class OperationImplication extends BinaryOperation {

    public OperationImplication(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        CharSequence left = leftExpression.toString();
        CharSequence right = rightExpression.toString();

        if (leftExpression instanceof OperationImplication)
            left = "(" + left + ")";

        if (rightExpression instanceof OperationImplication)
            right = "(" + right + ")";

        return left + "->" + right;
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr :
                new OperationImplication(leftExpression.replace(oldExpr, newExpr), rightExpression.replace(oldExpr, newExpr)));
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof OperationImplication &&
                leftExpression.equal(((OperationImplication) obj).getLeftExpression()) &&
                rightExpression.equal(((OperationImplication) obj).getRightExpression()));
    }
}
