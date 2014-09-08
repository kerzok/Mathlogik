package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public abstract class Quantifier implements Expression {
    final Token token;
    final Expression expression;

    Quantifier(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    @Override
    public String toString() {
        CharSequence var = token.toString();
        CharSequence expr = expression.toString();

        return var + "(" + expr + ")";
    }

    public Expression getExpression() {
        return expression;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        ArrayList<Token> result = new ArrayList<>();
        boolean isBusy = busyTokens.contains(token);
        if (!isBusy) {
            busyTokens.add(token);
        }
        result.addAll(expression.getFreeTokens(busyTokens));
        if (!isBusy) {
            busyTokens.remove(token);
        }
        return result;
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.add(token);
        result.addAll(expression.getBusyTokens());
        return result;
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.add(token);
        result.addAll(expression.getAllTokens());
        return result;
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        if (expression instanceof Quantifier) {
            Pair<Expression, Expression> difference = token.getDifferentExpression(((Quantifier) expression).getToken());
            if (difference != null) {
                return difference;
            }
            return this.expression.getDifferentExpression(((Quantifier) expression).getExpression());
        }
        return new Pair<>(this, expression);
    }
}
