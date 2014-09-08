package ru.smelik.mathlogik.task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
class Axioms {
    private ArrayList<String> axioms;

    public Axioms() {
        try (BufferedReader in = new BufferedReader(new FileReader("axioms.txt"))) {
            axioms = new ArrayList<>();
            String temp;
            while ((temp = in.readLine()) != null) {
                axioms.add(temp);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAxiom(Expression expression) throws MyException {
        if (axiom1(expression)) return true;
        if (axiom2(expression)) return true;
        if (axiom3(expression)) return true;
        if (axiom4(expression)) return true;
        if (axiom5(expression)) return true;
        if (axiom6(expression)) return true;
        if (axiom7(expression)) return true;
        if (axiom8(expression)) return true;
        if (axiom9(expression)) return true;
        if (axiom10(expression)) return true;
        if (axiom11(expression)) return true;
        return axiom12(expression);
    }

    private boolean axiom1(Expression expression) {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof OperationImplication) {
            OperationImplication mhs = (OperationImplication)expression;
            OperationImplication rhs = (OperationImplication)mhs.getRightExpression();
            String check = axioms.get(0);
            check = check.replaceAll("#", "(" + mhs.getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + rhs.getLeftExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom2(Expression expression) {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof OperationImplication) {
            Expression lhs = ((OperationImplication) expression).getLeftExpression();
            Expression rhs = ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getRightExpression();
            if (lhs instanceof OperationImplication && rhs instanceof OperationImplication) {
                String check = axioms.get(1);
                check = check.replaceAll("#", "(" + ((OperationImplication) lhs).getLeftExpression().toString() + ")");
                check = check.replaceAll("/", "(" + ((OperationImplication) lhs).getRightExpression().toString() + ")");
                check = check.replaceAll("%", "(" + ((OperationImplication) rhs).getRightExpression().toString() + ")");
                return expression.equals(new Parser(check).parse());
            }
        }
        return false;
    }

    private boolean axiom3(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof OperationAnd) {
            String check = axioms.get(2);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getRightExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom4(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof OperationAnd) {
            String check = axioms.get(3);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getRightExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom5(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationAnd) {
            String check = axioms.get(4);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationAnd) ((OperationImplication) expression).getRightExpression()).getRightExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom6(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationOr) {
            String check = axioms.get(5);
            check = check.replaceAll("#", "(" +((OperationImplication) expression).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationOr) ((OperationImplication) expression).getRightExpression()).getRightExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom7(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationOr) {
            String check = axioms.get(6);
            check = check.replaceAll("#", "(" + ((OperationOr) ((OperationImplication) expression).getRightExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationImplication) expression).getLeftExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom8(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationImplication) {
            Expression lhs = ((OperationImplication) expression).getLeftExpression();
            Expression mhs = ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getLeftExpression();
            if (lhs instanceof OperationImplication && mhs instanceof OperationImplication) {
                String check = axioms.get(7);
                check = check.replaceAll("#", "(" + ((OperationImplication) lhs).getLeftExpression().toString() + ")");
                check = check.replaceAll("/", "(" + ((OperationImplication) mhs).getLeftExpression().toString() + ")");
                check = check.replaceAll("%", "(" + ((OperationImplication) lhs).getRightExpression().toString() + ")");
                return expression.equals(new Parser(check).parse());
            }
        }
        return false;
    }

    private boolean axiom9(Expression expression) {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof OperationImplication) {
            String check = axioms.get(8);
            check = check.replaceAll("#", "(" + ((OperationImplication) ((OperationImplication) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationImplication) ((OperationImplication) expression).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom10(Expression expression) {
        if (expression instanceof OperationImplication) {
            String check = axioms.get(9);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getRightExpression().toString() + ")");
            return expression.equals(new Parser(check).parse());
        }
        return false;
    }

    boolean axiom11(Expression expression) throws MyException {
        if (expression instanceof OperationImplication) {
            OperationImplication implication = (OperationImplication) expression;
            if (implication.getLeftExpression() instanceof Universal) {
                Pair<Expression, Expression> difference = ((Universal) implication.getLeftExpression()).
                        getExpression().getDifferentExpression(implication.getRightExpression());
                if (difference == null) {
                    return checkFreeToSubstitutionIn11Axiom(expression);
                }
                if (difference.getFirst().equals(((Universal) implication.getLeftExpression()).getToken())) {
                    Expression sub = ((Universal) implication.getLeftExpression()).getExpression().
                            replace(difference.getFirst(), difference.getSecond());
                    return sub.equals(implication.getRightExpression()) &&
                            checkFreeToSubstitutionIn11Axiom(expression);
                }
            }
        }
        return false;
    }

    boolean axiom12(Expression expression) throws MyException{
        if (expression instanceof OperationImplication) {
            OperationImplication implication = (OperationImplication) expression;
            if (implication.getRightExpression() instanceof Existential) {
                Pair<Expression, Expression> difference = ((Existential) implication.getRightExpression()).
                        getExpression().getDifferentExpression(implication.getLeftExpression());
                if (difference == null) {
                    return checkFreeToSubstitutionIn12Axiom(expression);
                }
                if (difference.getFirst().equals(((Existential) implication.getRightExpression()).getToken())) {
                    Expression sub = ((Existential) implication.getRightExpression()).getExpression().
                            replace(difference.getFirst(), difference.getSecond());
                    return sub.equals(implication.getLeftExpression()) &&
                            checkFreeToSubstitutionIn12Axiom(expression);
                }
            }
        }
        return false;
    }

    private boolean checkFreeToSubstitutionIn11Axiom(Expression expression) throws MyException {
        Universal universal = (Universal)((OperationImplication) expression).getLeftExpression();
        Expression term = ((OperationImplication) expression).getRightExpression();
        Pair<Expression, Expression> difference = universal.getExpression().getDifferentExpression(term);
        term = difference != null ? difference.getSecond() : null;

        ArrayList<Token> busyTokens = universal.getExpression().getBusyTokens();
        ArrayList<Token> termTokens = (term != null) ? term.getAllTokens() : null;

        if (term != null && !universal.getToken().equals(term)) {
            for (Token var : termTokens) {
                if (busyTokens.contains(var)) {
                    throw new MyException("Вывод некорректен начиная с формулы " + Deduction.size + ": " +
                            "терм " + term.toString() + " не свободен для подстановки в формулу " +
                            universal.getExpression().toString() + " вместо переменной " + universal.getToken().toString());
                }
            }
        }
        return true;
    }

    private boolean checkFreeToSubstitutionIn12Axiom(Expression expression) throws MyException {
        Existential existential = (Existential)((OperationImplication) expression).getRightExpression();
        Expression term = ((OperationImplication) expression).getLeftExpression();
        Pair<Expression, Expression> difference = existential.getExpression().getDifferentExpression(term);
        term = difference != null ? difference.getSecond() : null;

        ArrayList<Token> busyTokens = existential.getExpression().getBusyTokens();
        ArrayList<Token> termTokens = (term != null) ? term.getAllTokens() : null;

        if (term != null && !existential.getToken().equals(term)) {
            for (Token var : termTokens) {
                if (busyTokens.contains(var)) {
                    throw new MyException("Вывод некорректен начиная с формулы " + Deduction.size + ": " +
                            "терм " + term.toString() + " не свободен для подстановки в формулу " +
                            existential.getExpression().toString() + " вместо переменной " + existential.getToken().toString());
                }
            }
        }
        return true;
    }
}


