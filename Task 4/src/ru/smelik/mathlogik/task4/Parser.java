package ru.smelik.mathlogik.task4;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
class Parser {

    final private String expression;

    public Parser(String expression) {
        this.expression = expression;
    }

    public Expression parse() {
        return parse(0, expression.length() - 1);
    }

    private Expression parse(int leftChar, int rightChar) {
        //Implication
        int bracketsCount = 0;
        for (int i = leftChar; i < rightChar; i++) {
            if (expression.charAt(i) == '>' && bracketsCount == 0) {
                return new OperationImplication(parse(leftChar, i - 2), parse(i + 1, rightChar));
            }
            if (expression.charAt(i) == '(') {
                bracketsCount++;
            }
            if (expression.charAt(i) == ')') {
                bracketsCount--;
            }
        }

        //Or
        bracketsCount = 0;
        for (int i = leftChar; i < rightChar; i++) {
            if (expression.charAt(i) == '|' && bracketsCount == 0) {
                return new OperationOr(parse(leftChar, i - 1), parse(i + 1, rightChar));
            }
            if (expression.charAt(i) == '(') {
                bracketsCount++;
            }
            if (expression.charAt(i) == ')') {
                bracketsCount--;
            }
        }

        //And
        bracketsCount = 0;
        for (int i = leftChar; i < rightChar; i++) {
            if (expression.charAt(i) == '&' && bracketsCount == 0) {
                return new OperationAnd(parse(leftChar, i - 1), parse(i + 1, rightChar));
            }
            if (expression.charAt(i) == '(') {
                bracketsCount++;
            }
            if (expression.charAt(i) == ')') {
                bracketsCount--;
            }
        }

        //Not
        if (expression.charAt(leftChar) == '!') {
            return new OperationNot(parse(leftChar + 1, rightChar));
        }

        //Parentheses
        bracketsCount = 0;
        if (expression.charAt(leftChar) == '(' && expression.charAt(rightChar) == ')') {
            boolean flag = true;
            for (int i = leftChar + 1; i < rightChar; i++) {
                if (expression.charAt(i) == '(') {
                    bracketsCount++;
                }
                if (expression.charAt(i) == ')') {
                    bracketsCount--;
                }
                if (bracketsCount == -1) {
                    flag = false;
                }
            }
            if (flag && bracketsCount == 0) {
                return parse(leftChar + 1, rightChar - 1);
            }
        }

        //Quantifiers
        // ∀ Universal
        if (expression.charAt(leftChar) == '@') {
            int index = leftChar + 1;
            while (index <= rightChar && Character.isDigit(expression.charAt(index)) ||
                            Character.isAlphabetic(expression.charAt(index)) &&
                            Character.isLowerCase(expression.charAt(index))) {
                index++;
            }
            Token var = new Token(expression.substring(leftChar + 1, index));
            return new Universal(var, parse(leftChar + var.toString().length() + 1, rightChar));
        }

        // Ǝ Existential
        if (expression.charAt(leftChar) == '?') {
            int index = leftChar + 1;
            while (index <= rightChar && Character.isDigit(expression.charAt(index)) ||
                    Character.isAlphabetic(expression.charAt(index)) &&
                            Character.isLowerCase(expression.charAt(index))) {
                index++;
            }
            Token var = new Token(expression.substring(leftChar + 1, index));
            return new Existential(var, parse(leftChar + var.toString().length() + 1, rightChar));
        }

        //Predicate
        bracketsCount = 0;
        if (Character.isAlphabetic(expression.charAt(leftChar))) {
            int index = leftChar;
            while (index <= rightChar && (Character.isDigit(expression.charAt(index)) ||
                    Character.isAlphabetic(expression.charAt(index)))) {
                index++;
            }
            if (index > rightChar) {
                return new Token(expression.substring(leftChar, index));
            }

            String predicateName = expression.substring(leftChar, index);
            ArrayList<Expression> terms = new ArrayList<>();
            int lastPosAfterComma = index + 1;
            for (int i = index + 1; i < rightChar; i++) {
                if (expression.charAt(i) == '(') {
                    bracketsCount++;
                }
                if (expression.charAt(i) == ')') {
                    bracketsCount--;
                }
                if (expression.charAt(i) == ',' && bracketsCount == 0) {
                    terms.add(parse(lastPosAfterComma, i - 1));
                    lastPosAfterComma = i + 1;
                }
            }
            terms.add(parse(lastPosAfterComma, rightChar - 1));
            return new Predicate(predicateName, terms);
        }
        return null;
    }
}
