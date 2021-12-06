package Lab3;

import Lab4.FiniteAutomata;
import Lab5.Grammar;
import Lab6.RecursiveDescendent;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("Starting program 1 \n");
//        MyScanner scannerP1 = new MyScanner("src/main/resources/Problems/p1");
//        scannerP1.scanFile();
//        System.out.println();
//
//        System.out.println("Starting program 2 \n");
//        MyScanner scannerP2 = new MyScanner("src/main/resources/Problems/p2");
//        scannerP2.scanFile();
//        System.out.println();
//
//
//        System.out.println("Starting program 3 \n");
//        MyScanner scannerP3 = new MyScanner("src/main/resources/Problems/p3");
//        scannerP3.scanFile();
//        System.out.println();
//
//        System.out.println("Starting program 1 with error \n");
//        MyScanner scannerP1err = new MyScanner("src/main/resources/Problems/p1err");
//        scannerP1err.scanFile();
//        System.out.println();
//
//        FiniteAutomata(new FiniteAutomata("fa.in"));
        Grammar grammar = new Grammar("src/main/resources/g1.txt");
        System.out.println(grammar);
//        System.out.println(grammar.productionForNonTerminal("A"));
//        System.out.println(grammar.productionForNonTerminal("B"));
//        System.out.println(grammar.productionForNonTerminal("S"));
//        System.out.println(grammar.productionForNonTerminal("C"));

        RecursiveDescendent recursiveDescendent = new RecursiveDescendent(grammar,"0110");
        recursiveDescendent.expand();
        recursiveDescendent.advance();
        recursiveDescendent.expand();
        recursiveDescendent.advance();
        recursiveDescendent.back();
        recursiveDescendent.anotherTry();
        recursiveDescendent.advance();
        recursiveDescendent.expand();
        recursiveDescendent.back();
        recursiveDescendent.anotherTry();
        recursiveDescendent.advance();
        recursiveDescendent.expand();
        recursiveDescendent.advance();
        recursiveDescendent.success();
        System.out.println("WorkingStack " + recursiveDescendent.configurationDTO.workingStack);
        System.out.println("InputStack " + recursiveDescendent.configurationDTO.inputStack);
        System.out.println("The grammar is CFG: " + grammar.isCFG());

    }

    private static void FiniteAutomataMenu() {
        System.out.println("1. Display Fa");
        System.out.println("2. Print States");
        System.out.println("3. Check DFA");
        System.out.println("4. Check sequence");
        System.out.println("5. Exit");

    }

    public static void FiniteAutomata(FiniteAutomata finiteAutomata) {
        Scanner userInput = new Scanner(System.in);

        while (true) {
            FiniteAutomataMenu();
            System.out.println("Enter command: ");
            String command = userInput.nextLine();
            if (command.equals("1")) {
                System.out.println(finiteAutomata.toString());
            } else if (command.equals("2")) {
                System.out.println(finiteAutomata.getSetOfStates());
            } else if (command.equals("3")) {
                System.out.println(finiteAutomata.isDFA());
            } else if (command.equals("4")) {
                System.out.println("Enter sequence: ");
                String sequence = userInput.nextLine();
                System.out.println(finiteAutomata.isAcceptedSequence(sequence));
            } else if (command.equals("5")) {
                break;
            }
        }
    }
}
