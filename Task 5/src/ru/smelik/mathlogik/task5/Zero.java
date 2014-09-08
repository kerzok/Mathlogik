package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Nick Smelik.
 */
public class Zero implements Expression {

    public Zero() {
    }

    @Override
    public boolean equal(Object obj) {
        return obj instanceof Zero;
    }

    @Override
    public String toString() {
        return "0";
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        return new ArrayList<>();
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        return (equal(expression)) ? null : new Pair<>(this, expression);
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return equal(oldExpr) ? newExpr : this;
    }
}
