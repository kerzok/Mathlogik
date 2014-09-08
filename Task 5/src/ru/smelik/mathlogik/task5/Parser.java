package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
class Parser {
    enum token_value {
        TOKEN, ZERO, END,
        IMPL, AND, OR, NOT,
        UNIVERSAL, EXISTENTIAL,
        PLUS, MUL, PREDICATE,
        LP, RP, APOSTROPHE, COMMA,
        EQUAL
    }

    private final String expression;
    private token_value cur_token;
    private int next;
    private String var;

    Parser(String expression) {
        this.expression = expression.replaceAll("->", ">");
    }

    token_value peek_token() throws MyException {
        if (next + 1 > expression.length())
            return cur_token = token_value.END;
        Character ch = expression.charAt(next++);

        switch (ch) {
            case '*':
                return cur_token = token_value.MUL;
            case '+':
                return cur_token = token_value.PLUS;
            case '(':
                return cur_token = token_value.LP;
            case ')':
                return cur_token = token_value.RP;
            case '\'':
                return cur_token = token_value.APOSTROPHE;
            case '@':
                return cur_token = token_value.UNIVERSAL;
            case '?':
                return cur_token = token_value.EXISTENTIAL;
            case '>':
                return cur_token = token_value.IMPL;
            case '&':
                return cur_token = token_value.AND;
            case '|':
                return cur_token = token_value.OR;
            case '!':
                return cur_token = token_value.NOT;
            case ',':
                return cur_token = token_value.COMMA;
            case '=':
                return cur_token = token_value.EQUAL;
            case '0':
                return cur_token = token_value.ZERO;
            default:
                if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
                    var = ch.toString();
                    while (next < expression.length() && (Character.isDigit(expression.charAt(next))/* ||
                            (Character.isAlphabetic(expression.charAt(next)))*/)) {
                        var += expression.charAt(next++);
                    }

                    if (next < expression.length() && expression.charAt(next) == '(' && cur_token != token_value.UNIVERSAL && cur_token != token_value.EXISTENTIAL) {
                        return cur_token = token_value.PREDICATE;
                    } else {
                        return cur_token = token_value.TOKEN;
                    }
                }
                /*if ((Character.isAlphabetic(ch) && Character.isLowerCase(ch)) || Character.isDigit(ch)) {
                    var = ch.toString();
                    while (next < expression.length() && (Character.isDigit(expression.charAt(next)) ||
                            (Character.isAlphabetic(expression.charAt(next)) && Character.isLowerCase(expression.charAt(next))))) {
                        var += expression.charAt(next++);
                    }
                    return cur_token = token_value.TOKEN;
                }
                if ((Character.isAlphabetic(ch) && Character.isUpperCase(ch)) || Character.isDigit(ch)) {
                    var = ch.toString();
                    while (next < expression.length() && (Character.isDigit(expression.charAt(next)) ||
                            (Character.isAlphabetic(expression.charAt(next)) && Character.isUpperCase(expression.charAt(next))))) {
                        var += expression.charAt(next++);
                    }
                    return cur_token = token_value.PREDICATE;
                }*/
                throw new MyException("Can' parse :(");
        }
    }

    Expression prim() throws MyException {

        if (cur_token == token_value.TOKEN) {
            Expression token = new Token(var);
            peek_token();
            return token;
        } else if (cur_token == token_value.LP) {
            peek_token();
            Expression e = logikImpl();
            if (cur_token != token_value.RP) {
                throw new MyException("I want ')'");
            }
            peek_token();
            if (cur_token == token_value.APOSTROPHE) {
                peek_token();
                return new Apostrophe(e);
            }
            return e;
        } else if (cur_token == token_value.NOT) {
            peek_token();
            return new OperationNot(prim());
        } else if (cur_token == token_value.EXISTENTIAL) {
            peek_token();
            if (cur_token == token_value.TOKEN) {
                Token token = new Token(var);
                peek_token();
                Expression e = prim();
                return new Existential(token, e);
            }
        } else if (cur_token == token_value.UNIVERSAL) {
            peek_token();
            if (cur_token == token_value.TOKEN) {
                Token token = new Token(var);
                peek_token();
                Expression e = prim();
                return new Universal(token, e);
            }
        } else if (cur_token == token_value.PREDICATE) {
            String predicateName = var;
            peek_token();
            if (cur_token == token_value.LP) {
                peek_token();
                ArrayList<Expression> terms = new ArrayList<>();
                while (cur_token != token_value.RP) {
                    terms.add(logikImpl());
                    if (cur_token == token_value.COMMA) {
                        peek_token();
                    }
                }
                peek_token();
                return new Predicate(predicateName, terms);
            }
        } else if (cur_token == token_value.ZERO) {
            peek_token();
            return new Zero();
        }
        throw new MyException("Unrecognized token :(");
    }

    Expression arifmApostrophe() throws MyException {
        Expression expr = prim();
        for (; ; ) {
            if (cur_token == token_value.APOSTROPHE) {
                peek_token();
                expr = new Apostrophe(expr);
            } else {
                return expr;
            }
        }
    }

    Expression arifmMul() throws MyException {
        Expression left = arifmApostrophe();

        for (; ; ) {
            switch (cur_token) {
                case MUL:
                    peek_token();
                    left = new Mul(left, arifmApostrophe());
                    break;
                default:
                    return left;
            }
        }
    }

    Expression arifmSum() throws MyException {
        Expression left = arifmMul();

        for (; ; ) {
            switch (cur_token) {
                case PLUS:
                    peek_token();
                    left = new Sum(left, arifmMul());
                    break;
                default:
                    return left;
            }
        }
    }

    Expression arifmEqual() throws MyException {
        Expression left = arifmSum();

        for (; ; ) {
            if (cur_token == token_value.EQUAL) {
                peek_token();
                left = new Equality(left, arifmSum());
            } else {
                return left;
            }
        }
    }

    Expression logikAnd() throws MyException {
        Expression left = arifmEqual();

        for (; ; ) {
            if (cur_token == token_value.AND) {
                peek_token();
                left = new OperationAnd(left, arifmEqual());
            } else {
                return left;
            }
        }
    }

    Expression logikOr() throws MyException {
        Expression left = logikAnd();

        for (; ; ) {
            if (cur_token == token_value.OR) {
                peek_token();
                left = new OperationOr(left, logikAnd());
            } else {
                return left;
            }
        }
    }

    Expression logikImpl() throws MyException {
        Expression left = logikOr();

        for (; ; ) {
            if (cur_token == token_value.IMPL) {
                peek_token();
                left = new OperationImplication(left, logikImpl());
            } else {
                return left;
            }
        }
    }

    Expression parse() throws MyException {
        peek_token();
        if (cur_token == token_value.END) {
            throw new MyException("empty input");
        }
        return logikImpl();
    }
}
