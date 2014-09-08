package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class Mul extends BinaryOperation {

    Mul(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        return "(" + leftExpression.toString() + "*" + rightExpression.toString() + ")";
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof Mul) &&
                leftExpression.equal(((Mul) obj).getLeftExpression()) &&
                rightExpression.equal(((Mul) obj).getRightExpression());
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr :
                new Mul(leftExpression.replace(oldExpr, newExpr), rightExpression.replace(oldExpr, newExpr)));
    }
}
