package ru.smelik.mathlogik.task5;

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
        if (axiom12(expression)) return true;
        if (arithmeticAxiom1(expression)) return true;
        if (arithmeticAxiom2(expression)) return true;
        if (arithmeticAxiom3(expression)) return true;
        if (arithmeticAxiom4(expression)) return true;
        if (arithmeticAxiom5(expression)) return true;
        if (arithmeticAxiom6(expression)) return true;
        if (arithmeticAxiom7(expression)) return true;
        //noinspection SimplifiableIfStatement
        if (arithmeticAxiom8(expression)) return true;
        return arithmeticAxiom9(expression);
    }

    private boolean axiom1(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof OperationImplication) {
            OperationImplication mhs = (OperationImplication) expression;
            OperationImplication rhs = (OperationImplication) mhs.getRightExpression();
            String check = axioms.get(0);
            check = check.replaceAll("#", "(" + mhs.getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + rhs.getLeftExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom2(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof OperationImplication) {
            Expression lhs = ((OperationImplication) expression).getLeftExpression();
            Expression rhs = ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getRightExpression();
            if (lhs instanceof OperationImplication && rhs instanceof OperationImplication) {
                String check = axioms.get(1);
                check = check.replaceAll("#", "(" + ((OperationImplication) lhs).getLeftExpression().toString() + ")");
                check = check.replaceAll("/", "(" + ((OperationImplication) lhs).getRightExpression().toString() + ")");
                check = check.replaceAll("%", "(" + ((OperationImplication) rhs).getRightExpression().toString() + ")");
                return expression.equal(new Parser(check).parse());
            }
        }
        return false;
    }

    private boolean axiom3(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof OperationImplication &&
                ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getRightExpression() instanceof OperationAnd) {
            String check = axioms.get(2);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getLeftExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom4(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof OperationAnd) {
            String check = axioms.get(3);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getRightExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom5(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof OperationAnd) {
            String check = axioms.get(4);
            check = check.replaceAll("#", "(" + ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + (((OperationImplication) expression).getRightExpression()).toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom6(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationOr) {
            String check = axioms.get(5);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationOr) ((OperationImplication) expression).getRightExpression()).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom7(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationOr) {
            String check = axioms.get(6);
            check = check.replaceAll("#", "(" + ((OperationOr) ((OperationImplication) expression).getRightExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationImplication) expression).getLeftExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom8(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof OperationImplication) {
            Expression lhs = ((OperationImplication) expression).getLeftExpression();
            Expression mhs = ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getLeftExpression();
            if (lhs instanceof OperationImplication && mhs instanceof OperationImplication) {
                String check = axioms.get(7);
                check = check.replaceAll("#", "(" + ((OperationImplication) lhs).getLeftExpression().toString() + ")");
                check = check.replaceAll("/", "(" + ((OperationImplication) mhs).getLeftExpression().toString() + ")");
                check = check.replaceAll("%", "(" + ((OperationImplication) lhs).getRightExpression().toString() + ")");
                return expression.equal(new Parser(check).parse());
            }
        }
        return false;
    }

    private boolean axiom9(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof OperationImplication) {
            String check = axioms.get(8);
            check = check.replaceAll("#", "(" + ((OperationImplication) ((OperationImplication) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((OperationImplication) ((OperationImplication) expression).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean axiom10(Expression expression) throws MyException {
        if (expression instanceof OperationImplication) {
            String check = axioms.get(9);
            check = check.replaceAll("#", "(" + ((OperationImplication) expression).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
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
                if (difference.getFirst().equal(((Universal) implication.getLeftExpression()).getToken())) {
                    Expression sub = ((Universal) implication.getLeftExpression()).getExpression().
                            replace(difference.getFirst(), difference.getSecond());
                    return sub.equal(implication.getRightExpression()) &&
                            checkFreeToSubstitutionIn11Axiom(expression);
                }
            }
        }
        return false;
    }

    boolean axiom12(Expression expression) throws MyException {
        if (expression instanceof OperationImplication) {
            OperationImplication implication = (OperationImplication) expression;
            if (implication.getRightExpression() instanceof Existential) {
                Pair<Expression, Expression> difference = ((Existential) implication.getRightExpression()).
                        getExpression().getDifferentExpression(implication.getLeftExpression());
                if (difference == null) {
                    return checkFreeToSubstitutionIn12Axiom(expression);
                }
                if (difference.getFirst().equal(((Existential) implication.getRightExpression()).getToken())) {
                    Expression sub = ((Existential) implication.getRightExpression()).getExpression().
                            replace(difference.getFirst(), difference.getSecond());
                    return sub.equal(implication.getLeftExpression()) &&
                            checkFreeToSubstitutionIn12Axiom(expression);
                }
            }
        }
        return false;
    }

    @SuppressWarnings("SameReturnValue")
    private boolean checkFreeToSubstitutionIn11Axiom(Expression expression) throws MyException {
        Universal universal = (Universal) ((OperationImplication) expression).getLeftExpression();
        Expression term = ((OperationImplication) expression).getRightExpression();
        Pair<Expression, Expression> difference = universal.getExpression().getDifferentExpression(term);
        term = difference != null ? difference.getSecond() : null;

        ArrayList<Token> busyTokens = universal.getExpression().getBusyTokens();
        ArrayList<Token> termTokens = (term != null) ? term.getAllTokens() : null;

        if (term != null && !universal.getToken().equal(term)) {
            for (Token var : termTokens) {
                if (busyTokens.contains(var)) {
                    throw new MyException("Вывод некорректен начиная со строки " + Deduction.size + ": " +
                            "терм " + term.toString() + " не свободен для подстановки в формулу " +
                            universal.getExpression().toString() + " вместо переменной " + universal.getToken().toString());
                }
            }
        }
        return true;
    }

    @SuppressWarnings("SameReturnValue")
    private boolean checkFreeToSubstitutionIn12Axiom(Expression expression) throws MyException {
        Existential existential = (Existential) ((OperationImplication) expression).getRightExpression();
        Expression term = ((OperationImplication) expression).getLeftExpression();
        Pair<Expression, Expression> difference = existential.getExpression().getDifferentExpression(term);
        term = difference != null ? difference.getSecond() : null;

        ArrayList<Token> busyTokens = existential.getExpression().getBusyTokens();
        ArrayList<Token> termTokens = (term != null) ? term.getAllTokens() : null;

        if (term != null && !existential.getToken().equal(term)) {
            for (Token var : termTokens) {
                if (busyTokens.contains(var)) {
                    throw new MyException("Вывод некорректен начиная со строки " + Deduction.size + ": " +
                            "терм " + term.toString() + " не свободен для подстановки в формулу " +
                            existential.getExpression().toString() + " вместо переменной " + existential.getToken().toString());
                }
            }
        }
        return true;
    }

    private boolean arithmeticAxiom1(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getLeftExpression() instanceof Equality) {
            String check = axioms.get(10);
            check = check.replaceAll("#", "(" + ((Equality) ((OperationImplication) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((Equality) ((OperationImplication) expression).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom2(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof OperationImplication &&
                ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getLeftExpression() instanceof Equality &&
                ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getRightExpression() instanceof Equality) {
            String check = axioms.get(11);
            check = check.replaceAll("#", "(" + ((Equality) ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("%", "(" + ((Equality) ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getRightExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((Equality) ((OperationImplication) ((OperationImplication) expression).getRightExpression()).getLeftExpression()).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom3(Expression expression) throws MyException {
        if (expression instanceof OperationImplication && ((OperationImplication) expression).getRightExpression() instanceof Equality) {
            String check = axioms.get(12);
            check = check.replaceAll("#", "(" + ((Equality) ((OperationImplication) expression).getRightExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((Equality) ((OperationImplication) expression).getRightExpression()).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom4(Expression expression) throws MyException {
        if (expression instanceof Equality &&
                ((Equality) expression).getLeftExpression() instanceof Apostrophe &&
                ((Apostrophe) ((Equality) expression).getLeftExpression()).getExpression() instanceof OperationNot) {
            String check = axioms.get(13);
            check = check.replaceAll("#", "(" + ((OperationNot) ((Apostrophe) ((Equality) expression).getLeftExpression()).getExpression()).getExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom5(Expression expression) throws MyException {
        if (expression instanceof Equality &&
                ((Equality) expression).getLeftExpression() instanceof Sum &&
                ((Sum) ((Equality) expression).getLeftExpression()).getRightExpression() instanceof Apostrophe) {
            String check = axioms.get(14);
            check = check.replaceAll("#", "(" + ((Sum) ((Equality) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((Apostrophe) ((Sum) ((Equality) expression).getLeftExpression()).getRightExpression()).getExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom6(Expression expression) throws MyException {
        if (expression instanceof Equality) {
            String check = axioms.get(15);
            check = check.replaceAll("#", "(" + ((Equality) expression).getRightExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom7(Expression expression) throws MyException {
        if (expression instanceof Equality &&
                ((Equality) expression).getLeftExpression() instanceof Mul) {
            String check = axioms.get(16);
            check = check.replaceAll("#", "(" + ((Mul) ((Equality) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom8(Expression expression) throws MyException {
        if (expression instanceof Equality &&
                ((Equality) expression).getLeftExpression() instanceof Mul &&
                ((Mul) ((Equality) expression).getLeftExpression()).getRightExpression() instanceof Apostrophe) {
            String check = axioms.get(17);
            check = check.replaceAll("#", "(" + ((Mul) ((Equality) expression).getLeftExpression()).getLeftExpression().toString() + ")");
            check = check.replaceAll("/", "(" + ((Apostrophe) ((Mul) ((Equality) expression).getLeftExpression()).getRightExpression()).getExpression().toString() + ")");
            return expression.equal(new Parser(check).parse());
        }
        return false;
    }

    private boolean arithmeticAxiom9(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getLeftExpression() instanceof OperationAnd &&
                ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getRightExpression() instanceof Universal) {
            Universal universal = (Universal) ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getRightExpression();
            Token x = universal.getToken();
            if (universal.getExpression() instanceof OperationImplication) {
                boolean term1 = ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getLeftExpression().equal(((OperationImplication) expression).getRightExpression().replace(x, new Zero()));
                boolean term2 = ((OperationImplication) expression).getRightExpression().equal(((OperationImplication) universal.getExpression()).getLeftExpression());
                boolean term3 = ((OperationImplication) universal.getExpression()).getRightExpression().equal(((OperationImplication) expression).getRightExpression().replace(x, new Apostrophe(x)));
                return term1 && term2 && term3 && checkFreeToSubstitutionIn9Axiom(expression);
            }
        }
        return false;
    }

    @SuppressWarnings("SameReturnValue")
    private boolean checkFreeToSubstitutionIn9Axiom(Expression expression) throws MyException {
        Token checkToken = ((Universal) ((OperationAnd) ((OperationImplication) expression).getLeftExpression()).getRightExpression()).getToken();
        ArrayList<Token> busyTokens = ((OperationImplication) expression).getRightExpression().getBusyTokens();
        if (busyTokens.contains(checkToken)) {
            throw new MyException("Вывод некорректен начиная со строки " + Deduction.size + ": " +
                    "терм " + (new Apostrophe(checkToken)) + " не свободен для подстановки в формулу " +
                    ((OperationImplication) expression).getRightExpression() + " вместо переменной " + checkToken);
        }
        return true;
    }
}


