package ru.smelik.mathlogik.task2;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class Token implements Expression {
    private String tokenName;

    public Token(String tokenName) {
        this.tokenName = tokenName;
    }

    @Override
    public boolean evaluate(ArrayList<String> tokenList) {
        return tokenList.contains(tokenName);
    }

    @Override
    public String toString() {
        return tokenName;
    }

    @Override
    public boolean equals(Expression expression) {
        return tokenName.equals(expression.toString());
    }
}
