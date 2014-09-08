package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public interface Expression {
    public String toString();

    public boolean equal(Object o);

    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens);

    public ArrayList<Token> getBusyTokens();

    public ArrayList<Token> getAllTokens();

    public Pair<Expression, Expression> getDifferentExpression(Expression expression);

    public Expression replace(Expression oldExpr, Expression newExpr);
}
