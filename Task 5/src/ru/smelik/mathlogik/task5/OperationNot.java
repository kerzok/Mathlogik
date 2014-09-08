package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class OperationNot implements Expression {
    final private Expression expression;

    public OperationNot(Expression expression) {
        this.expression = expression;
    }

    Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        CharSequence expr = expression.toString();
        if (expression instanceof Token ||
                expression instanceof Existential ||
                expression instanceof Universal ||
                expression instanceof Predicate) {
            return "!" + expr;
        } else {
            return "!(" + expr + ")";
        }
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(expression.getFreeTokens(busyTokens));
        return result;
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(expression.getBusyTokens());
        return result;
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> result = new ArrayList<>();
        result.addAll(expression.getAllTokens());
        return result;
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        if (expression instanceof OperationNot) {
            return this.expression.getDifferentExpression(((OperationNot) expression).getExpression());
        }
        return new Pair<>(this, expression);
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        return (equal(oldExpr) ? newExpr : new OperationNot(expression.replace(oldExpr, newExpr)));
    }

    @Override
    public boolean equal(Object obj) {
        return obj instanceof OperationNot &&
                expression.equal(((OperationNot) obj).getExpression());
    }
}
