package ru.smelik.matlogik.task1;

import java.io.*;
import java.util.ArrayList;

public class Main {

    static ArrayList<Expression> tokensList = new ArrayList<Expression>();

    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            FileWriter output = new FileWriter("output.txt");

            try {
                String currentLine;
                int lineNumber = 0;
                boolean check = true;
                while ((currentLine = input.readLine()) != null) {
                    lineNumber++;
                    Parser parser = new Parser(currentLine);
                    tokensList.add(parser.parse());
                    if (!Axioms.isAxiom(tokensList)) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    output.write("Доказательство корректно");
                } else {
                    output.write("Доказательство некорректно начиная с высказывания № " + lineNumber);
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
