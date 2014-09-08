package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class Sum extends BinaryOperation {

    Sum(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        return "(" + leftExpression.toString() + "+" + rightExpression.toString() + ")";
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof Sum) &&
                leftExpression.equal(((Sum) obj).getLeftExpression()) &&
                rightExpression.equal(((Sum) obj).getRightExpression());
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr :
                new Sum(leftExpression.replace(oldExpr, newExpr), rightExpression.replace(oldExpr, newExpr)));
    }
}
