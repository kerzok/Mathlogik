package ru.smelik.mathlogik.task2;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            FileWriter output = new FileWriter("output.txt");

            try {
                String currentLine;
                int lineNumber = 0;
                Deduction deduction = new Deduction(input.readLine());

                boolean check = true;
                while ((currentLine = input.readLine()) != null) {
                    if (currentLine.length() == 0) continue;
                    lineNumber++;
                    Parser parser = new Parser(currentLine);
                    if (!deduction.add(parser.parse())) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    ArrayList<Expression> mainProof = deduction.getMainProof();
                    for (Expression item : mainProof) {
                        output.write(item.toString() + '\n');
                    }
                } else {
                    output.write("Исходный вывод некорректен. Строка № " + lineNumber);
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
