package ru.smelik.mathlogik.task4;

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
        return (equals(oldExpr) ? newExpr :
                new Universal((Token) token.replace(oldExpr, newExpr), expression.replace(oldExpr, newExpr)));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Universal && super.equals(obj);
    }
}
