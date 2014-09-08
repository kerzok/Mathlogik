package ru.smelik.mathlogik.task5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Main {

    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            FileWriter output = new FileWriter("output.txt");
            Deduction deduction = new Deduction();
            String expression;
            try {
                while ((expression = input.readLine()) != null) {
                    if (expression.isEmpty()) continue;
                    deduction.addExpression(new Parser(expression).parse());
                }
                output.write("Доказательство корректно");
            } catch (MyException e) {
                output.write(e.toString());
            }

            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
