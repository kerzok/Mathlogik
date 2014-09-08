package ru.smelik.mathlogik.task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Smelik Nick.
 */
public class Axioms {

    static boolean isAxiom(Expression token) {
        if (isFirstAxiom(token)) return true;
        if (isSecondAxiom(token)) return true;
        if (isThirdAxiom(token)) return true;
        if (isFourthAxiom(token)) return true;
        if (isFifthAxiom(token)) return true;
        if (isSixthAxiom(token)) return true;
        if (isSeventhAxiom(token)) return true;
        if (isEighthAxiom(token)) return true;
        if (isNinthAxiom(token)) return true;
        if (isTenthAxiom(token)) return true;
        return false;
    }

    private static boolean isFirstAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom1.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getRightToken() instanceof OperationImplication) {
                    s = s.replaceAll("#", "(" + ((OperationImplication) token).getLeftToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationImplication) ((OperationImplication) token).getRightToken()).getLeftToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isSecondAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom2.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getRightToken() instanceof OperationImplication) {
                    Expression leftToken = ((OperationImplication) token).getLeftToken();
                    Expression rightToken = ((OperationImplication) ((OperationImplication) token).getRightToken()).getRightToken();
                    if (leftToken instanceof OperationImplication && rightToken instanceof OperationImplication) {
                        s = s.replaceAll("#", "(" + ((OperationImplication) leftToken).getLeftToken().toString() + ")");
                        s = s.replaceAll("/", "(" + ((OperationImplication) leftToken).getRightToken().toString() + ")");
                        s = s.replaceAll("%", "(" + ((OperationImplication) rightToken).getRightToken().toString() + ")");
                        return token.equals(new Parser(s).parse());
                    }
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isThirdAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom3.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getRightToken() instanceof OperationImplication) {
                    s = s.replaceAll("#", "(" + ((OperationImplication) token).getLeftToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationImplication) ((OperationImplication) token).getRightToken()).getLeftToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isFourthAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom4.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getLeftToken() instanceof OperationAnd) {
                    s = s.replaceAll("#", "(" + ((OperationImplication) token).getRightToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationAnd) ((OperationImplication) token).getLeftToken()).getRightToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isFifthAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom5.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getLeftToken() instanceof OperationAnd) {
                    s = s.replaceAll("#", "(" + ((OperationAnd) ((OperationImplication) token).getLeftToken()).getLeftToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationImplication) token).getRightToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isSixthAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom6.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getRightToken() instanceof OperationOr) {
                    s = s.replaceAll("#", "(" + ((OperationImplication) token).getLeftToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationOr) ((OperationImplication) token).getRightToken()).getRightToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isSeventhAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom7.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getRightToken() instanceof OperationOr) {
                    s = s.replaceAll("#", "(" + ((OperationOr) ((OperationImplication) token).getRightToken()).getLeftToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationImplication) token).getLeftToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isEighthAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom8.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getRightToken() instanceof OperationImplication) {
                    Expression leftToken = ((OperationImplication) token).getLeftToken();
                    Expression middleToken = ((OperationImplication) ((OperationImplication) token).getRightToken()).getLeftToken();
                    if (leftToken instanceof OperationImplication && middleToken instanceof OperationImplication) {
                        s = s.replaceAll("#", "(" + ((OperationImplication) leftToken).getLeftToken().toString() + ")");
                        s = s.replaceAll("/", "(" + ((OperationImplication) middleToken).getLeftToken().toString() + ")");
                        s = s.replaceAll("%", "(" + ((OperationImplication) leftToken).getRightToken().toString() + ")");
                        return token.equals(new Parser(s).parse());
                    }
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isNinthAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom9.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication && ((OperationImplication) token).getLeftToken() instanceof OperationImplication) {
                    s = s.replaceAll("#", "(" + ((OperationImplication) ((OperationImplication) token).getLeftToken()).getLeftToken().toString() + ")");
                    s = s.replaceAll("/", "(" + ((OperationImplication) ((OperationImplication) token).getLeftToken()).getRightToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private static boolean isTenthAxiom(Expression token) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("axiom10.txt"));
            try {
                String s = input.readLine();
                if (token instanceof OperationImplication) {
                    s = s.replaceAll("#", "(" + ((OperationImplication) token).getRightToken().toString() + ")");
                    return token.equals(new Parser(s).parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return false;
    }
}
