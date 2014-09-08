package ru.smelik.mathlogik.task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nick.
 */
public class Proofs {
    
    private static final String AT = "@";
    private static final String SHARP = "#";

    private static ArrayList<Expression> tokensList;
    public static ArrayList<Expression> prove(Expression expression, ArrayList<String> trueTokens) {
        tokensList = new ArrayList<>();
        recursion(expression, trueTokens);
        return tokensList;
    }

    private static boolean recursion(Expression expression, ArrayList<String> trueTokens) {
        if (expression instanceof OperationImplication) {
            boolean leftFlag = recursion(((OperationImplication) expression).getLeftToken(), trueTokens);
            boolean rightFlag = recursion(((OperationImplication) expression).getRightToken(), trueTokens);
            if (leftFlag && rightFlag) {
                add(expression5(((OperationImplication) expression).getLeftToken(), ((OperationImplication) expression).getRightToken()));
                return true;
            } else
            if (leftFlag && !rightFlag) {
                add(expression6(((OperationImplication) expression).getLeftToken(), new OperationNot(((OperationImplication) expression).getRightToken())));
                return false;
            } else
            if (!leftFlag && rightFlag) {
                add(expression7(new OperationNot(((OperationImplication) expression).getLeftToken()), ((OperationImplication) expression).getRightToken()));
                return true;
            } else {
                add(expression8(new OperationNot(((OperationImplication) expression).getLeftToken()), new OperationNot(((OperationImplication) expression).getRightToken())));
                return true;
            }
        }
        if (expression instanceof OperationOr) {
            boolean leftFlag = recursion(((OperationOr) expression).getLeftToken(), trueTokens);
            boolean rightFlag = recursion(((OperationOr) expression).getRightToken(), trueTokens);
            if (leftFlag && rightFlag) {
                add(expression9(((OperationOr) expression).getLeftToken(),((OperationOr) expression).getRightToken()));
                return true;
            } else
            if (leftFlag && !rightFlag) {
                add(expression10(((OperationOr) expression).getLeftToken(), new OperationNot(((OperationOr) expression).getRightToken())));
                return true;
            } else
            if (!leftFlag && rightFlag) {
                add(expression11(new OperationNot(((OperationOr) expression).getLeftToken()), ((OperationOr) expression).getRightToken()));
                return true;
            } else {
                add(expression12(new OperationNot(((OperationOr) expression).getLeftToken()), new OperationNot(((OperationOr) expression).getRightToken())));
                return false;
            }
        }
        if (expression instanceof OperationAnd) {
            boolean leftFlag = recursion(((OperationAnd) expression).getLeftToken(), trueTokens);
            boolean rightFlag = recursion(((OperationAnd) expression).getRightToken(), trueTokens);
            if (leftFlag && rightFlag) {
                add(expression1(((OperationAnd) expression).getLeftToken(), ((OperationAnd) expression).getRightToken()));
                return true;
            } else
            if (leftFlag && !rightFlag) {
                add(expression2(((OperationAnd) expression).getLeftToken(), new OperationNot(((OperationAnd) expression).getRightToken())));
                return false;
            } else
            if (!leftFlag && rightFlag) {
                add(expression3(new OperationNot(((OperationAnd) expression).getLeftToken()), ((OperationAnd) expression).getRightToken()));
                return false;
            }  else {
                add(expression4(new OperationNot(((OperationAnd) expression).getLeftToken()), new OperationNot(((OperationAnd) expression).getRightToken())));
                return false;
            }
        }

        if (expression instanceof OperationNot) {
            boolean flag = recursion(((OperationNot) expression).getExpression(), trueTokens);
            if (flag) {
                add(expression13(((OperationNot) expression).getExpression()));

                return false;
            } else {
                add(expression14(new OperationNot(((OperationNot) expression).getExpression())));

                if (((OperationNot) expression).getExpression() instanceof OperationNot) {
                    add(expression15(((OperationNot) expression)));
                }
                return true;
            }
        }

        if (expression instanceof Token) {
            if (trueTokens.contains(expression.toString())) {
                add(expression13(expression));
                return true;
            }
            else
                return false;
        }

        return true;
    }

    private static void add(ArrayList<Expression> tmp) {
        for (Expression item : tmp)
            tokensList.add(item);
    }

    // P, Q |- (P&Q)
    static ArrayList<Expression> expression1(Expression exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression1.txt"));
            try {
                input = new BufferedReader(new FileReader("expression1.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, !Q |- !(P&Q)
    static ArrayList<Expression> expression2(Expression exprP, OperationNot exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression2.txt"));
            try {
                input = new BufferedReader(new FileReader("expression2.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, Q |- !(P&Q)
    static ArrayList<Expression> expression3(OperationNot exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression3.txt"));
            try {
                input = new BufferedReader(new FileReader("expression3.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, !Q |- !(P&Q)
    static ArrayList<Expression> expression4(OperationNot exprP, OperationNot exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression4.txt"));
            try {
                input = new BufferedReader(new FileReader("expression4.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, Q |- (P->Q)
    static ArrayList<Expression> expression5(Expression exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression5.txt"));
            try {
                input = new BufferedReader(new FileReader("expression5.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, !Q |- !(P->Q)
    static ArrayList<Expression> expression6(Expression exprP, OperationNot exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression6.txt"));
            try {
                input = new BufferedReader(new FileReader("expression6.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, Q |- (P->Q) ???
    static ArrayList<Expression> expression7(OperationNot exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression7.txt"));
            try {
                input = new BufferedReader(new FileReader("expression7.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, !Q |- (P->Q)
    static ArrayList<Expression> expression8(OperationNot exprP, OperationNot exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression8.txt"));
            try {
                input = new BufferedReader(new FileReader("expression8.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, Q |- (P|Q)
    static ArrayList<Expression> expression9(Expression exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression9.txt"));
            try {
                input = new BufferedReader(new FileReader("expression9.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P, !Q |- (P|Q)
    static ArrayList<Expression> expression10(Expression exprP, OperationNot exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression10.txt"));
            try {
                input = new BufferedReader(new FileReader("expression10.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, Q |- (P|Q)
    static ArrayList<Expression> expression11(OperationNot exprP, Expression exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression11.txt"));
            try {
                input = new BufferedReader(new FileReader("expression11.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P, !Q |- !(P|Q)
    static ArrayList<Expression> expression12(OperationNot exprP, OperationNot exprQ) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression12.txt"));
            try {
                input = new BufferedReader(new FileReader("expression12.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    s = s.replaceAll(SHARP, "(" + exprQ.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // P |- !(!P)
    static ArrayList<Expression> expression13(Expression exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression13.txt"));
            try {
                input = new BufferedReader(new FileReader("expression13.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !P |- !P
    static ArrayList<Expression> expression14(OperationNot exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression14.txt"));
            try {
                input = new BufferedReader(new FileReader("expression14.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.getExpression().toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    // !!P |- P
    static ArrayList<Expression> expression15(OperationNot exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression15.txt"));
            try {
                input = new BufferedReader(new FileReader("expression15.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    if (exprP.getExpression() instanceof OperationNot) {
                        s = s.replaceAll(AT, "(" + ((OperationNot) exprP.getExpression()).getExpression().toString() + ")");
                        Parser parser = new Parser(s);
                        result.add(parser.parse());
                    }
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

    public static ArrayList<Expression> expression16(Expression exprP) {
        ArrayList<Expression> result = new ArrayList<Expression>();
        try {
            BufferedReader input = new BufferedReader(new FileReader("expression16.txt"));
            try {
                input = new BufferedReader(new FileReader("expression16.txt"));
                String s;
                while ((s = input.readLine()) != null) {
                    s = s.replaceAll(AT, "(" + exprP.toString() + ")");
                    Parser parser = new Parser(s);
                    result.add(parser.parse());
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return result;
    }
}
