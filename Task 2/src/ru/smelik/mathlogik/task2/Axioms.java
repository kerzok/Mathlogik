package ru.smelik.mathlogik.task2;

/**
 * Created by Smelik Nick.
 */
public class Axioms {

    static boolean isAxiom(Expression expression) {
        if (isFirstAxiom(expression)) return true;
        if (isSecondAxiom(expression)) return true;
        if (isThirdAxiom(expression)) return true;
        if (isFourthAxiom(expression)) return true;
        if (isFifthAxiom(expression)) return true;
        if (isSixthAxiom(expression)) return true;
        if (isSeventhAxiom(expression)) return true;
        if (isEighthAxiom(expression)) return true;
        if (isNinthAxiom(expression)) return true;
        if (isTenth(expression)) return true;
        return false;
    }

    private static boolean isFirstAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            OperationImplication expression = (OperationImplication) token;
            Expression leftToken = expression.getLeftToken();
            Expression rightToken = expression.getRightToken();

            if (rightToken instanceof OperationImplication) {
                if (leftToken.equals(((OperationImplication) rightToken).getRightToken())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSecondAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            Expression leftToken = ((OperationImplication) token).getLeftToken();
            Expression rightToken = ((OperationImplication) token).getRightToken();

            if ((leftToken instanceof OperationImplication) && (rightToken instanceof OperationImplication)) {
                Expression rightRightToken = ((OperationImplication) rightToken).getRightToken();
                Expression rightLeftToken = ((OperationImplication) rightToken).getLeftToken();

                if ((rightLeftToken instanceof OperationImplication) && (rightRightToken instanceof OperationImplication)) {
                    if (((OperationImplication) rightLeftToken).getRightToken() instanceof OperationImplication)
                        if (((OperationImplication) leftToken).getLeftToken().equals(((OperationImplication) rightLeftToken).getLeftToken()) &&
                                ((OperationImplication) leftToken).getLeftToken().equals(((OperationImplication) rightRightToken).getLeftToken()) &&
                                ((OperationImplication) leftToken).getRightToken().equals(((OperationImplication) ((OperationImplication) rightLeftToken).getRightToken()).getLeftToken()) &&
                                ((OperationImplication) ((OperationImplication) rightLeftToken).getRightToken()).getRightToken().equals(((OperationImplication) rightRightToken).getRightToken())) {
                            return true;
                        }
                }
            }
        }
        return false;
    }

    private static boolean isThirdAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            if (((OperationImplication) token).getRightToken() instanceof OperationImplication) {
                if (((OperationImplication) ((OperationImplication) token).getRightToken()).getRightToken() instanceof OperationAnd) {
                    if (((OperationImplication) token).getLeftToken().equals(((OperationAnd) ((OperationImplication) ((OperationImplication) token).getRightToken()).getRightToken()).getLeftToken()) &&
                            ((OperationImplication) ((OperationImplication) token).getRightToken()).getLeftToken().equals(((OperationAnd) ((OperationImplication) ((OperationImplication) token).getRightToken()).getRightToken()).getRightToken())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isFourthAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            if (((OperationImplication) token).getLeftToken() instanceof OperationAnd) {
                if (((OperationAnd) ((OperationImplication) token).getLeftToken()).getLeftToken().equals(((OperationImplication) token).getRightToken())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isFifthAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            if (((OperationImplication) token).getLeftToken() instanceof OperationAnd) {
                if (((OperationAnd) ((OperationImplication) token).getLeftToken()).getRightToken().equals(((OperationImplication) token).getRightToken())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSixthAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            if (((OperationImplication) token).getRightToken() instanceof OperationOr) {
                if (((OperationImplication) token).getLeftToken().equals(((OperationOr) ((OperationImplication) token).getRightToken()).getLeftToken())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isSeventhAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            if (((OperationImplication) token).getRightToken() instanceof OperationOr) {
                if (((OperationImplication) token).getLeftToken().equals(((OperationOr) ((OperationImplication) token).getRightToken()).getRightToken())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isEighthAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            Expression leftToken = ((OperationImplication) token).getLeftToken();
            Expression rightToken = ((OperationImplication) token).getRightToken();

            if ((leftToken instanceof OperationImplication) && (rightToken instanceof OperationImplication)) {
                if ((((OperationImplication) rightToken).getRightToken() instanceof OperationImplication) &&
                        (((OperationImplication) rightToken).getLeftToken() instanceof OperationImplication)) {
                    if (((OperationImplication) ((OperationImplication) rightToken).getRightToken()).getLeftToken() instanceof OperationOr) {
                        if (((OperationImplication) leftToken).getLeftToken().equals(((OperationOr) ((OperationImplication) ((OperationImplication) rightToken).getRightToken()).getLeftToken()).getLeftToken()) &&
                                ((OperationImplication) leftToken).getRightToken().equals(((OperationImplication) ((OperationImplication) rightToken).getLeftToken()).getRightToken()) &&
                                ((OperationImplication) leftToken).getRightToken().equals(((OperationImplication) ((OperationImplication) rightToken).getRightToken()).getRightToken()) &&
                                ((OperationImplication) ((OperationImplication) rightToken).getLeftToken()).getLeftToken().equals(((OperationOr) ((OperationImplication) ((OperationImplication) rightToken).getRightToken()).getLeftToken()).getRightToken())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isNinthAxiom(Expression token) {
        if (token instanceof OperationImplication) {
            Expression leftToken = ((OperationImplication) token).getLeftToken();
            Expression rightToken = ((OperationImplication) token).getRightToken();

            if (leftToken instanceof OperationImplication && rightToken instanceof OperationImplication) {
                if (((OperationImplication) rightToken).getRightToken() instanceof OperationNot &&
                        ((OperationImplication) rightToken).getLeftToken() instanceof OperationImplication) {
                    if (((OperationImplication) ((OperationImplication) rightToken).getLeftToken()).getRightToken() instanceof OperationNot) {
                        if (((OperationImplication) leftToken).getLeftToken().equals(((OperationImplication) ((OperationImplication) rightToken).getLeftToken()).getLeftToken()) &&
                                ((OperationImplication) leftToken).getLeftToken().equals(((OperationNot) ((OperationImplication) rightToken).getRightToken()).getExpression()) &&
                                ((OperationImplication) leftToken).getRightToken().equals(((OperationNot) ((OperationImplication) ((OperationImplication) rightToken).getLeftToken()).getRightToken()).getExpression())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isTenth(Expression token) {
        if (token instanceof OperationImplication) {
            if (((OperationImplication) token).getLeftToken() instanceof OperationNot) {
                if (((OperationNot) ((OperationImplication) token).getLeftToken()).getExpression() instanceof OperationNot) {
                    if (((OperationNot) ((OperationNot) ((OperationImplication) token).getLeftToken()).getExpression()).getExpression().equals(((OperationImplication) token).getRightToken())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
