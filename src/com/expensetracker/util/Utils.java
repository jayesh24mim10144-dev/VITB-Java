package com.expensetracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Utils {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    public static LocalDate parseDateOrToday(String s) {
        try {
            return LocalDate.parse(s, DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Using today.");
            return LocalDate.now();
        }
    }

    public static String readString(String prompt, Scanner sc) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static int readInt(String prompt, Scanner sc) {
        while (true) {
            String in = readString(prompt, sc);
            try {
                return Integer.parseInt(in);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    public static double readDouble(String prompt, Scanner sc) {
        while (true) {
            String in = readString(prompt, sc);
            try {
                double v = Double.parseDouble(in);
                if (v <= 0) { System.out.println("Enter a positive number."); continue; }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    public static String formatMoney(double amt) {
        return String.format("₹%.2f", amt);
    }

    public static String currentYearMonth() {
        java.time.YearMonth ym = java.time.YearMonth.now();
        return ym.toString();
    }
}
