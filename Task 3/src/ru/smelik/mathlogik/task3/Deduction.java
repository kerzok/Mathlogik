package ru.smelik.mathlogik.task3;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class Deduction {
    private final static String COMA = ",";
    private final static String DEDUCTABLE = "|-";

    private ArrayList<Expression> gamma;
    private ArrayList<Expression> mainProof;
    private ArrayList<Expression> inputExpressions;

    private Expression alpha = null;
    private Expression betta = null;
    private String s;

    public Deduction(String s) {
        this.s = s;
        expressionParse();
    }

    public Expression getAlpha() {
        return alpha;
    }

    public Expression getBetta() {
        return betta;
    }

    public ArrayList<Expression> getMainProof() {
        return mainProof;
    }

    public boolean add(Expression expression) {
        inputExpressions.add(expression);

        if (alpha == null) {
            mainProof.add(expression);
            return true;
        }

        if (isAxiom(expression)) {
            mainProof.add(expression);
            mainProof.add(new OperationImplication(expression, new OperationImplication(alpha, expression)));
            mainProof.add(new OperationImplication(alpha, expression));

            return true;
        }

        if (isGamma(expression)) {
            return true;
        }

        if (expression.equals(alpha)) {
            Expression leftToken = new OperationImplication(alpha, new OperationImplication(alpha, alpha));
            Expression middleToken = new OperationImplication(alpha, new OperationImplication(new OperationImplication(alpha, alpha), alpha));
            Expression rightToken = new OperationImplication(alpha, alpha);

            mainProof.add(new OperationImplication(alpha, new OperationImplication(alpha, alpha)));
            mainProof.add(new OperationImplication(leftToken, new OperationImplication(middleToken, rightToken)));
            mainProof.add(new OperationImplication(middleToken, rightToken));
            mainProof.add(middleToken);
            mainProof.add(new OperationImplication(alpha, alpha));

            return true;
        }

        if (mainProofModusPonensCheck(expression))
            return true;

        if (mainProofAlphaGammaModusPonensCheck(expression))
            return true;

        if (alphaGammaAndGammaModusPonensCheck(expression))
            return true;

        return false;
    }

    private boolean alphaGammaAndGammaModusPonensCheck(Expression expression) {
        for (int i = 0; i < gamma.size(); i++) {
            if (gamma.get(i) instanceof OperationImplication && ((OperationImplication) gamma.get(i)).getRightToken().equals(expression)) {
                for (int j = 0; j < gamma.size(); j++) {
                    if (gamma.get(j).equals(((OperationImplication) gamma.get(i)).getLeftToken())) {
                        makeChange(expression, gamma.get(j));
                        return true;
                    }
                }
                if (alpha.equals(((OperationImplication) gamma.get(i)).getLeftToken())) {
                    makeChange(expression, alpha);
                    return true;
                }
            }
            if (gamma.get(i) instanceof OperationImplication && ((OperationImplication) gamma.get(i)).getLeftToken().equals(expression)) {
                for (int j = 0; j < gamma.size(); j++) {
                    if (gamma.get(j).equals(((OperationImplication) gamma.get(i)).getRightToken())) {
                        makeChange(expression, gamma.get(j));
                        return true;
                    }
                }
                if (alpha.equals(((OperationImplication) gamma.get(i)).getRightToken())) {
                    makeChange(expression, gamma.get(i));
                }
            }
        }
        return false;
    }

    private boolean mainProofAlphaGammaModusPonensCheck(Expression expression) {
        for (int i = inputExpressions.size() - 1; i >= 0; i--) {
            if (inputExpressions.get(i) instanceof OperationImplication && ((OperationImplication) inputExpressions.get(i)).getRightToken().equals(expression)) {
                for (int j = 0; j < gamma.size(); j++) {
                    if (((OperationImplication) inputExpressions.get(i)).getLeftToken().equals(gamma.get(j))) {
                        makeChange(expression, gamma.get(j));
                        return true;
                    }
                }
                if (((OperationImplication) inputExpressions.get(i)).getLeftToken().equals(alpha)) {
                    makeChange(expression, alpha);
                    return true;
                }
            }

            if (inputExpressions.get(i) instanceof OperationImplication && ((OperationImplication) inputExpressions.get(i)).getLeftToken().equals(expression)) {
                for (int j = 0; j < gamma.size(); j++) {
                    if (((OperationImplication) inputExpressions.get(i)).getRightToken().equals(gamma.get(j))) {
                        makeChange(expression, inputExpressions.get(i));
                        return true;
                    }
                }
                if (((OperationImplication) inputExpressions.get(i)).getRightToken().equals(alpha)) {
                    makeChange(expression, inputExpressions.get(i));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGamma(Expression expression) {
        for (int i = 0; i < gamma.size(); i++) {
            if (expression.equals(gamma.get(i))) {
                mainProof.add(expression);
                mainProof.add(new OperationImplication(expression, new OperationImplication(alpha, expression)));
                mainProof.add(new OperationImplication(alpha, expression));
                return true;
            }
        }
        return false;
    }

    private boolean mainProofModusPonensCheck(Expression expression) {
        for (int i = inputExpressions.size() - 1; i >= 0; i--) {
            if (inputExpressions.get(i) instanceof OperationImplication && ((OperationImplication) inputExpressions.get(i)).getRightToken().equals(expression)) {
                for (int j = inputExpressions.size() - 1; j >= 0; j--) {
                    if (((OperationImplication) inputExpressions.get(i)).getLeftToken().equals(inputExpressions.get(j))) {
                        makeChange(expression, inputExpressions.get(j));
                        return true;
                    }
                }
            }

            if (inputExpressions.get(i) instanceof OperationImplication && ((OperationImplication) inputExpressions.get(i)).getLeftToken().equals(expression)) {
                for (int j = inputExpressions.size() - 1; j >= 0; j--) {
                    if (((OperationImplication) inputExpressions.get(i)).getRightToken().equals(inputExpressions.get(j))) {
                        makeChange(expression, inputExpressions.get(i));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void makeChange(Expression exp1, Expression exp2) {
        Expression leftToken = new OperationImplication(alpha, exp2);
        Expression middleToken = new OperationImplication(alpha, new OperationImplication(exp2, exp1));
        Expression rightToken = new OperationImplication(alpha, exp1);

        mainProof.add(new OperationImplication(leftToken, new OperationImplication(middleToken, rightToken)));
        mainProof.add(new OperationImplication(middleToken, rightToken));
        mainProof.add(rightToken);
    }

    private boolean isAxiom(Expression expression) {
        return Axioms.isAxiom(expression);
    }

    private void expressionParse() {
        gamma = new ArrayList<Expression>();
        mainProof = new ArrayList<Expression>();
        inputExpressions = new ArrayList<Expression>();

        Parser parser;
        while (s.indexOf(",") >= 0) {
            String temp = s.substring(0, s.indexOf(COMA));
            parser = new Parser(temp);
            gamma.add(parser.parse());
            s = s.substring(s.indexOf(COMA) + 1, s.length());
        }

        if (s.indexOf(DEDUCTABLE) == 0) {
            alpha = null;
            return;
        }

        String temp = s.substring(0, s.indexOf(DEDUCTABLE));
        parser = new Parser(temp);
        alpha = parser.parse();
        temp = s.substring(s.indexOf(DEDUCTABLE) + 2, s.length());
        parser = new Parser(temp);
        betta = parser.parse();
    }

}
