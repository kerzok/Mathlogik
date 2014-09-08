package ru.smelik.mathlogik.task2;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public interface Expression {
    public abstract boolean evaluate(ArrayList<String> tokenList);
    public String toString();
    public boolean equals(Expression expression);
}
