package ru.smelik.mathlogik.task4;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class Predicate implements Expression {

    final private String name;
    final private ArrayList<Expression> terms;

    public Predicate(String name, ArrayList<Expression> terms) {
        this.name = name;
        this.terms = terms;
    }

    String getName() {
        return name;
    }

    ArrayList<Expression> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        String result = name;
        if (terms != null && terms.size() != 0) {
            result += '(';
        }
        if (terms != null) {
            for (int i = 0; i < terms.size() - 1; i++) {
                result += terms.get(i).toString() + ',';
            }
            result += terms.get(terms.size() - 1);
        }
        if (terms != null && terms.size() != 0) {
            result += ')';
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Predicate) {
            boolean flag = name.equals(((Predicate) obj).getName()) &&
                    terms.size() == ((Predicate) obj).getTerms().size();
            for (int i = 0; i < terms.size() && flag; i++) {
                if (!terms.get(i).equals(((Predicate) obj).getTerms().get(i))) {
                    flag = false;
                }
            }
            return flag;
        }
        return false;
    }

    @Override
    public ArrayList<Token> getFreeTokens(ArrayList<Token> busyTokens) {
        ArrayList<Token> result = new ArrayList<>();
        for (Expression term : terms) {
            result.addAll(term.getFreeTokens(busyTokens));
        }
        return result;
    }

    @Override
    public ArrayList<Token> getBusyTokens() {
        ArrayList<Token> result = new ArrayList<>();
        for (Expression term : terms) {
            result.addAll(term.getBusyTokens());
        }
        return result;
    }

    @Override
    public ArrayList<Token> getAllTokens() {
        ArrayList<Token> result = new ArrayList<>();
        for (Expression term : terms) {
            result.addAll(term.getAllTokens());
        }
        return result;
    }

    @Override
    public Pair<Expression, Expression> getDifferentExpression(Expression expression) {
        if (expression instanceof Predicate) {
            if (!name.equals(((Predicate) expression).name)) {
                return new Pair<>(this, expression);
            }
            ArrayList<Expression> terms = ((Predicate) expression).terms;
            if (this.terms.size() != terms.size()) {
                return new Pair<>(this, expression);
            }
            for (int i = 0; i < terms.size(); i++) {
                Pair<Expression, Expression> difference = this.terms.get(i).getDifferentExpression(terms.get(i));
                if (difference != null) {
                    return difference;
                }
            }
            return null;
        }
        return new Pair<>(this, expression);
    }

    @Override
    public Expression replace(Expression oldExpr, Expression newExpr) {
        if (equals(oldExpr)) {
            return newExpr;
        }
        ArrayList<Expression> terms = new ArrayList<>();
        for (Expression term : this.terms) {
            terms.add(term.replace(oldExpr, newExpr));
        }
        return new Predicate(name, terms);
    }
}
