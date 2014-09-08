package ru.smelik.mathlogik.task3;

import java.util.ArrayList;

/**
 * Created by Smelik Nick.
 */
public class Prove {
    private String s;
    private ArrayList<String> allTokens;
    private ArrayList<Expression>[] allProofs;
    private Expression inputExpression;

    public Prove(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }

    public ArrayList<Expression> getResult() {
        allTokens = IsTrueExpression.getAllVariables(s);
        allProofs = new ArrayList[(1 << allTokens.size())];
        inputExpression = new Parser(s).parse();
        setAllProofs();
        return getAnswer(1);
    }

    private ArrayList<Expression> getAnswer(int n) {
        if (n >= (1 << allTokens.size())) {
            return allProofs[n - (1 << allTokens.size())];
        } else {
            return merge(getAnswer(2 * n), getAnswer(2 * n + 1), 2 * n);
        }
    }

    private ArrayList<Expression> merge(ArrayList<Expression> left, ArrayList<Expression> right, int n) {
        int length = 0;
        int k = n;
        while (k > 0) {
            k = k / 2;
            length++;
        }
        length--;

        String request = getRequest(IsTrueExpression.getCode(n, length), length, left.get(left.size() - 1));
        Deduction deduction = new Deduction(request);
        for (int i = 0; i < left.size(); i++) {
            if (deduction.add(left.get(i))) { }
        }
        ArrayList<Expression> result = deduction.getMainProof();

        request = getRequest(IsTrueExpression.getCode(n + 1, length), length, right.get(right.size() - 1));
        deduction = new Deduction(request);
        for (int i = 0; i < right.size(); i++) {
            if (deduction.add(right.get(i))) { }
        }
        ArrayList<Expression> tempResult = deduction.getMainProof();

        for (int i = 0; i < tempResult.size(); i++) {
            result.add(tempResult.get(i));
        }

        Expression alpha = deduction.getAlpha();
        Expression betta = deduction.getBetta();

        if (alpha instanceof OperationNot) {
            alpha = ((OperationNot) alpha).getExpression();
        }
        ArrayList<Expression> alphaNotAlpha = Proofs.expression16(alpha);
        for (int i = 0; i < alphaNotAlpha.size(); i++) {
            result.add(alphaNotAlpha.get(i));
        }

        Expression leftToken = new OperationImplication(alpha, betta);
        Expression middleToken = new OperationImplication(new OperationNot(alpha), betta);
        Expression rightToken = new OperationImplication(new OperationOr(alpha, new OperationNot(alpha)), betta);

        result.add(new OperationImplication(leftToken, new OperationImplication(middleToken, rightToken)));
        result.add(new OperationImplication(middleToken, rightToken));
        result.add(rightToken);
        result.add(betta);

        return result;
    }

    private void setAllProofs() {
        for (int i = 0; i < (1 << allTokens.size()); i++) {
            ArrayList<String> trueTokens = getTrueTokens(i);
            allProofs[i] = new Proofs().prove(inputExpression, trueTokens);
        }
    }

    private String getRequest(String code, int length, Expression expression) {
        String request = "";
        for (int i = 0; i < length - 1; i++) {
            if (code.charAt(i) == '1') {
                request += allTokens.get(i) + ",";
            } else {
                request += "!" + allTokens.get(i) + ",";
            }
        }
        if (code.charAt(length - 1) == '1') {
            request += allTokens.get(length - 1) + "|-";
        } else {
            request += "!" + allTokens.get(length - 1) + "|-";
        }
        request += expression.toString();
        return request;
    }

    private ArrayList<String> getTrueTokens(int n) {
        ArrayList<String> trueTokens = new ArrayList<>();
        String str = IsTrueExpression.getCode(n, allTokens.size());
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == '1') {
                trueTokens.add(allTokens.get(j));
            } else {
                trueTokens.add("!" + allTokens.get(j));
            }
        }
        return trueTokens;
    }
}
