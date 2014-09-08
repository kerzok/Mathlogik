package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class OperationOr extends BinaryOperation {

    public OperationOr(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        CharSequence left = leftExpression.toString();
        CharSequence right = rightExpression.toString();

        if (leftExpression instanceof OperationOr ||
                leftExpression instanceof OperationImplication)
            left = "(" + left + ")";

        if (rightExpression instanceof OperationOr ||
                rightExpression instanceof OperationImplication)
            right = "(" + right + ")";

        return left + "|" + right;
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr :
                new OperationOr(leftExpression.replace(oldExpr, newExpr), rightExpression.replace(oldExpr, newExpr)));
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof OperationOr &&
                leftExpression.equal(((OperationOr) obj).getLeftExpression()) &&
                rightExpression.equal(((OperationOr) obj).getRightExpression()));
    }
}
