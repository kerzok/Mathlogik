package ru.smelik.mathlogik.task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Nick Smelik.
 */
class Deduction {
    private static final char COMMA = ',';
    private static final String DEDUCTABLE = "|-";

    public static int size;

    final private Axioms axioms;
    final private ArrayList<Expression> main;
    final private ArrayList<Expression> gamma;
    final private ArrayList<Expression> input;
    final private ArrayList<ArrayList<Token>> freeTokens;
    final private ArrayList<String> universalInferenceRule;
    final private ArrayList<String> existentialInferenceRule;


    public static Expression alpha = null;

    public Deduction(String inputExpression) {
        size = 0;
        axioms = new Axioms();
        main = new ArrayList<>();
        gamma = new ArrayList<>();
        input = new ArrayList<>();
        freeTokens = new ArrayList<>();
        universalInferenceRule = readFromFile("universal_rule.txt");
        existentialInferenceRule = readFromFile("existential_rule.txt");
        deductionParse(inputExpression);
    }

    public ArrayList<Expression> getMainProof() {
        return main;
    }

    private ArrayList<String> readFromFile(final String fileName) {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.add(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addExpression(Expression expression) throws MyException {
        size++;
        input.add(expression);
        if (axioms.isAxiom(expression)) {
            main.add(expression);
            main.add(new OperationImplication(expression, new OperationImplication(alpha, expression)));
            main.add(new OperationImplication(alpha, expression));
            return;
        }

        if (isGamma(expression) ||
                isAlpha(expression) ||
                isModusPonens(expression) ||
                isUniversalRule(expression) ||
                isExistentialRule(expression)) {
            return;
        }

        throw new MyException("Вывод некорректен начиная с формулы " + size);
    }

    private boolean isGamma(Expression expression) {
        for (Expression item : gamma) {
            if (expression.equals(item)) {
                main.add(expression);
                main.add(new OperationImplication(expression, new OperationImplication(alpha, expression)));
                main.add(new OperationImplication(alpha, expression));
                return true;
            }
        }
        return false;
    }

    private boolean isAlpha(Expression expression) {
        if (expression.equals(alpha)) {
            Expression lhs = new OperationImplication(alpha, new OperationImplication(alpha, alpha));
            Expression mhs = new OperationImplication(alpha, new OperationImplication(new OperationImplication(alpha, alpha), alpha));
            Expression rhs = new OperationImplication(alpha, alpha);
            main.add(new OperationImplication(alpha, new OperationImplication(alpha, alpha)));
            main.add(new OperationImplication(lhs, new OperationImplication(mhs, rhs)));
            main.add(new OperationImplication(mhs, rhs));
            main.add(mhs);
            main.add(new OperationImplication(alpha, alpha));
            return true;
        }
        return false;
    }

    private boolean isModusPonens(Expression expression) {
        for (int i = input.size() - 1; i >= 0; i--) {
            if (input.get(i) instanceof OperationImplication) {
                if (((OperationImplication) input.get(i)).getRightExpression().equals(expression)) {
                    for (int j = input.size() - 1; j >= 0; j--) {
                        Expression lhs = input.get(j);
                        if (lhs.equals(((OperationImplication) input.get(i)).getLeftExpression())) {
                            main.add(new OperationImplication(
                                        new OperationImplication(alpha, lhs),
                                        new OperationImplication(
                                            new OperationImplication(alpha,
                                                new OperationImplication(lhs, expression)),
                                            new OperationImplication(alpha, expression))));
                            main.add(new OperationImplication(
                                        new OperationImplication(alpha,
                                                new OperationImplication(lhs, expression)),
                                        new OperationImplication(alpha, expression)));
                            main.add(new OperationImplication(alpha, expression));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isExistentialRule(Expression expression) throws MyException{
        if (expression instanceof OperationImplication) {
            OperationImplication impl = (OperationImplication) expression;
            if (((OperationImplication) expression).getLeftExpression() instanceof Existential) {
                Existential exist = (Existential) ((OperationImplication) expression).getLeftExpression();
                for (int i = input.size() - 1; i >= 0; i--) {
                    if (input.get(i) instanceof OperationImplication) {
                        OperationImplication newImpl = (OperationImplication) input.get(i);
                        if (impl.getRightExpression().equals(newImpl.getRightExpression()) &&
                                exist.getExpression().equals(newImpl.getLeftExpression())) {
                            if (impl.getRightExpression().getAllTokens().contains(exist.getToken()) &&
                                    impl.getRightExpression().getFreeTokens(new ArrayList<>()).contains(exist.getToken())) {
                                throw new MyException("Вывод некорректен начиная с формулы " + size + ": " + "переменная " +
                                        exist.getToken().toString() + " входит свободно в формулу " + impl.getRightExpression().toString());
                            }
                            for (int j = 0; j < freeTokens.size(); j++) {
                                if (freeTokens.get(j).contains(exist.getToken())) {
                                    Expression temp = (j < gamma.size()) ? gamma.get(j) : alpha;
                                    throw new MyException("Вывод некорректен начиная с формулы " + size + ": " +
                                            "используется правило с квантором по переменной " + exist.getToken().toString() +
                                            ", входящей свободно в допущение " + temp.toString());
                                }
                            }

                            main.addAll(existentialInferenceRule.stream().map(item -> replace(item, alpha, exist.getExpression(), impl.getRightExpression(), exist.getToken())).collect(Collectors.toList()));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isUniversalRule(Expression expression) throws MyException {
        if (expression instanceof OperationImplication) {
            OperationImplication impl = (OperationImplication) expression;
            if (((OperationImplication) expression).getRightExpression() instanceof Universal) {
                Universal universal = (Universal) ((OperationImplication) expression).getRightExpression();
                for (int i = input.size() - 1; i >= 0; i--) {
                    if (input.get(i) instanceof OperationImplication) {
                        OperationImplication newImpl = (OperationImplication) input.get(i);
                        if (impl.getLeftExpression().equals(newImpl.getLeftExpression()) &&
                                universal.getExpression().equals(newImpl.getRightExpression())) {
                            if (impl.getLeftExpression().getAllTokens().contains(universal.getToken()) &&
                                    impl.getLeftExpression().getFreeTokens(new ArrayList<>()).contains(universal.getToken())) {
                                throw new MyException("Вывод некорректен начиная с формулы " + size + ": " + "переменная " +
                                        universal.getToken().toString() + " входит свободно в формулу " + impl.getLeftExpression().toString());
                            }
                            for (int j = 0; j < freeTokens.size(); j++) {
                                if (freeTokens.get(j).contains(universal.getToken())) {
                                    Expression temp = (j < gamma.size()) ? gamma.get(j) : alpha;
                                    throw new MyException("Вывод некорректен начиная с формулы " + size + ": " +
                                            "используется правило с квантором по переменной " + universal.getToken().toString() +
                                            ", входящей свободно в допущение " + temp.toString());
                                }
                            }

                            main.addAll(universalInferenceRule.stream().map(item -> replace(item, alpha, universal.getExpression(), impl.getLeftExpression(), universal.getToken())).collect(Collectors.toList()));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static Expression replace(String expression, Expression item1, Expression item2, Expression item3, Token token) {
        expression = expression.replaceAll("%", item1.toString());
        expression = expression.replaceAll("#", item2.toString());
        expression = expression.replaceAll("<", item3.toString());
        expression = expression.replaceAll("/", token.toString());
        return new Parser(expression).parse();
    }

    private void deductionParse(String expression) {
        int brackets = 0;
        int lastAfterComma = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(')
                brackets++;
            if (expression.charAt(i) == ')')
                brackets--;
            if (expression.charAt(i) == COMMA && brackets == 0) {
                gamma.add(new Parser(expression.substring(lastAfterComma, i)).parse());
                freeTokens.add((gamma.get(gamma.size() - 1)).getFreeTokens(new ArrayList<>()));
                lastAfterComma = i + 1;
            }
        }
        alpha = new Parser(expression.substring(lastAfterComma, expression.indexOf(DEDUCTABLE))).parse();
        freeTokens.add(alpha.getFreeTokens(new ArrayList<>()));
    }
}
