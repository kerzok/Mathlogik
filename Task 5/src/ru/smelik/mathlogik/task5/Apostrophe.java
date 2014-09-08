package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Nick Smelik.
 */
public class Apostrophe implements Expression {
    private final Expression expression;

    public Apostrophe(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof Apostrophe &&
                expression.equal(((Apostrophe) obj).getExpression()));
    }

    @Override
    public String toString() {
        return expression.toString() + "'";
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(expression.getFreeTokens(busyTokens));
        return result;
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        return expression.getBusyTokens();
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        return expression.getAllTokens();
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        return (expression instanceof Apostrophe) ?
                this.expression.getDifferentExpression(((Apostrophe) expression).getExpression()) :
                new Pair<>(this, expression);
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return equal(oldExpr) ? newExpr : new Apostrophe(expression.replace(oldExpr, newExpr));
    }
}
