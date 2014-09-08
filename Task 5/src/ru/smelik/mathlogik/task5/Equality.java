package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class Equality extends BinaryOperation {


    Equality(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        return "(" + leftExpression.toString() + "=" + rightExpression.toString() + ")";
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof Equality) &&
                leftExpression.equal(((Equality) obj).getLeftExpression()) &&
                rightExpression.equal(((Equality) obj).getRightExpression());
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr)) ? newExpr :
                new Equality(leftExpression.replace(oldExpr, newExpr), rightExpression.replace(oldExpr, newExpr));
    }
}
