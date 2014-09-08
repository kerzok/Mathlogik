package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class Token implements Expression {
    final private String name;

    public Token(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equal(Object obj) {
        return (obj instanceof Token) && name.equals(obj.toString());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Token) && equal(obj);
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        ArrayList<Token> result = new ArrayList<>();
        if (!busyTokens.contains(this)) {
            result.add(this);
        }
        return result;
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.add(this);
        return result;
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        if (expression.equal(this)) {
            return null;
        }
        return new Pair<>(this, expression);
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        if (oldExpr.equal(this)) {
            return newExpr;
        }
        return this;
    }
}
