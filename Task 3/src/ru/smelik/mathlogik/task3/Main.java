package ru.smelik.mathlogik.task3;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            FileWriter output = new FileWriter("output.txt");

            try {
                Prove prove = new Prove(input.readLine());
                String result = IsTrueExpression.getTrueFalseResult(prove.getString());
                if (result.equals("Высказывание истинно.")) {
                    ArrayList<Expression> answer = prove.getResult();
                    for (Expression item : answer) {
                        output.write(item.toString() + '\n');
                    }
                } else {
                    output.write(result.toString() + '\n');
                }
            } finally {
                input.close();
                output.close();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
}
