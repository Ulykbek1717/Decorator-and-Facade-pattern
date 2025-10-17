package main.ui;

import java.util.Scanner;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;

public class Input {
    public static int askInt(Scanner sc, String prompt) {
    int value;

    while (true) {
        System.out.print(prompt + " ");

        try {
            value = Integer.parseInt(sc.nextLine().trim());
            break; 
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer.");
        }
    }

    return value;
}


    public static int askInt(Scanner sc, String prompt, IntPredicate rule, String errMsg) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (rule.test(v)) return v;
                System.out.println(errMsg);
            } catch (Exception e) {
                System.out.println("Please enter an integer.");
            }
        }
    }

    public static double askDouble(Scanner sc, String prompt) {
    double value;

    while (true) {
        System.out.print(prompt + " ");

        try {
            value = Double.parseDouble(sc.nextLine().trim());
            break; 
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

        return value;
    }

    public static double askDouble(Scanner sc, String prompt, DoublePredicate rule, String errMsg) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
                if (rule.test(v)) return v;
                System.out.println(errMsg);
            } catch (Exception e) { System.out.println("Please enter a valid number."); }
        }
    }

    public static String askLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static boolean askYesNo(Scanner sc, String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine().trim().toLowerCase();
        return s.startsWith("y") || s.equals("yes");
    }
}
