package ru.smelik.mathlogik.task2;

/**
 * Created by Smelik Nick.
 */
public class Parser {
    private String expression;

    public Parser(String expression) {
        this.expression = expression;
    }

    Expression parse() {
        return parse(0, expression.length() - 1);
    }

    Expression parse(int leftChar, int rightChar) {

        int bracketsBalance = 0;
        for (int i = leftChar; i <= rightChar; i++) {
            if (expression.charAt(i) == '>' && bracketsBalance == 0) {
                return new OperationImplication(parse(leftChar, i - 2), parse(i + 1, rightChar));
            }
            if (expression.charAt(i) == ')')
                bracketsBalance--;
            if (expression.charAt(i) == '(')
                bracketsBalance++;
        }

        bracketsBalance = 0;
        for (int i = leftChar; i <= rightChar; i++) {
            if (expression.charAt(i) == '|' && bracketsBalance == 0) {
                return new OperationOr(parse(leftChar, i - 1), parse(i + 1, rightChar));
            }
            if (expression.charAt(i) == ')')
                bracketsBalance--;
            if (expression.charAt(i) == '(')
                bracketsBalance++;
        }

        bracketsBalance = 0;
        for (int i = leftChar; i <= rightChar; i++) {
            if (expression.charAt(i) == '&' && bracketsBalance == 0) {
                return new OperationAnd(parse(leftChar, i - 1), parse(i + 1, rightChar));
            }
            if (expression.charAt(i) == ')')
                bracketsBalance--;
            if (expression.charAt(i) == '(')
                bracketsBalance++;
        }

        if (expression.charAt(leftChar) == '!')
            return new OperationNot(parse(leftChar + 1, rightChar));

        if (expression.charAt(leftChar) == '(')
            return parse(leftChar + 1, rightChar - 1);

        return new Token(expression.substring(leftChar, rightChar + 1));
    }
}
