package ru.smelik.mathlogik.task5;

import java.util.ArrayList;

/**
 * Created by Nick Smelik.
 */
class Deduction {

    public static int size;

    final private Axioms axioms;
    final private ArrayList<Expression> input;

    public Deduction() {
        size = 0;
        axioms = new Axioms();
        input = new ArrayList<>();
    }

    public void addExpression(Expression expression) throws MyException {
        size++;
        input.add(expression);
        if (axioms.isAxiom(expression) ||
                isModusPonens(expression) ||
                isUniversalRule(expression) ||
                isExistentialRule(expression)) {
            return;
        }

        throw new MyException("Вывод некорректен начиная со строки " + size);
    }

    private boolean isModusPonens(Expression expression) {
        for (int i = input.size() - 1; i >= 0; i--) {
            if (input.get(i) instanceof OperationImplication) {
                if (((OperationImplication) input.get(i)).getRightExpression().equal(expression)) {
                    for (int j = input.size() - 1; j >= 0; j--) {
                        Expression lhs = input.get(j);
                        if (lhs.equal(((OperationImplication) input.get(i)).getLeftExpression())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isExistentialRule(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getLeftExpression() instanceof Existential) {
            Existential existential = (Existential) ((OperationImplication) expression).getLeftExpression();
            for (int i = size - 2; i >= 0; i--) {
                if (input.get(i) instanceof OperationImplication) {
                    OperationImplication implication = (OperationImplication) input.get(i);
                    if (((OperationImplication) expression).getRightExpression().equal(implication.getRightExpression()) &&
                            existential.getExpression().equal(implication.getRightExpression()) &&
                            ((OperationImplication) expression).getRightExpression().getFreeTokens(new ArrayList<>()).contains(existential.getToken())) {
                        throw new MyException("Вывод некорректен начиная со строки " + size + ": " + " переменная " +
                                existential.getToken().toString() + " входит свободно в формулу " + ((OperationImplication) expression).getRightExpression().toString());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUniversalRule(Expression expression) throws MyException {
        if (expression instanceof OperationImplication &&
                ((OperationImplication) expression).getRightExpression() instanceof Universal) {
            Universal universal = (Universal) ((OperationImplication) expression).getRightExpression();
            for (int i = size - 1; i >= 0; i--) {
                if (input.get(i) instanceof OperationImplication) {
                    OperationImplication implication = (OperationImplication) input.get(i);
                    if (((OperationImplication) expression).getLeftExpression().equal(implication.getLeftExpression()) &&
                            universal.getExpression().equal(implication.getLeftExpression()) &&
                            ((OperationImplication) expression).getLeftExpression().getFreeTokens(new ArrayList<>()).contains(universal.getToken())) {
                        throw new MyException("Вывод некорректен начиная со строки " + size + ": "
                                + "переменная " + universal.getToken().toString() + " входит свободно в формулу " +
                                ((OperationImplication) expression).getLeftExpression().toString());
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
