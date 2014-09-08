package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public abstract class BinaryOperation implements Expression {
    final Expression leftExpression;
    final Expression rightExpression;

    BinaryOperation(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    Expression getLeftExpression() {
        return leftExpression;
    }

    Expression getRightExpression() {
        return rightExpression;
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(leftExpression.getFreeTokens(busyTokens));
        result.addAll(rightExpression.getFreeTokens(busyTokens));
        return result;
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(leftExpression.getBusyTokens());
        result.addAll(rightExpression.getBusyTokens());
        return result;
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(leftExpression.getAllTokens());
        result.addAll(rightExpression.getAllTokens());
        return result;
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        if (expression instanceof BinaryOperation) {
            Pair<Expression, Expression> difference = leftExpression.getDifferentExpression(((BinaryOperation) expression).getLeftExpression());
            if (difference != null) {
                return difference;
            }
            return rightExpression.getDifferentExpression(((BinaryOperation) expression).getRightExpression());
        }
        return new Pair<>(this, expression);
    }

}
