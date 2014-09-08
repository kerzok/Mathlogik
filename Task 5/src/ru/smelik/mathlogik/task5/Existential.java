package ru.smelik.mathlogik.task5;

/**
 * Created by Smelik Nick.
 */
public class Existential extends Quantifier {

    Existential(Token token, Expression expression) {
        super(token, expression);
    }

    @Override
    public String toString() {
        return "?" + super.toString();
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr :
                new Existential((Token) token.replace(oldExpr, newExpr), expression.replace(oldExpr, newExpr)));
    }

    @Override
    public boolean equal(Object obj) {
        return obj instanceof Existential && expression.equal(((Existential) obj).getExpression());
    }
}
