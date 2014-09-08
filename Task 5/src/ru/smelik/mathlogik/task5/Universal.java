package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class Universal extends Quantifier {

    Universal(Token token, Expression expression) {
        super(token, expression);
    }

    @Override
    public String toString() {
        return "@" + super.toString();
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr :
                new Universal((Token) token.replace(oldExpr, newExpr), expression.replace(oldExpr, newExpr)));
    }

    @Override
    public boolean equal(Object obj) {
        return obj instanceof Universal && expression.equal(((Universal) obj).getExpression());
    }
}
