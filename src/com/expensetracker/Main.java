package com.expensetracker;

import com.expensetracker.core.ExpenseManager;
import com.expensetracker.core.ReportGenerator;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.util.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final ExpenseManager manager = new ExpenseManager();
    private static final ReportGenerator reports = new ReportGenerator(manager);

    public static void main(String[] args) {
        System.out.println("=== Personal Expense Tracker ===");
        manager.loadAll(); 
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = Utils.readInt("Choose an option: ", SC);
            switch (choice) {
                case 1 -> addExpenseFlow();
                case 2 -> listExpensesFlow();
                case 3 -> deleteExpenseFlow();
                case 4 -> manageCategoriesFlow();
                case 5 -> manageBudgetsFlow();
                case 6 -> reportsFlow();
                case 7 -> {
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }
    }

    private static void printMainMenu() {
        System.out.println("""
                1. Add Expense
                2. List Expenses
                3. Delete Expense
                4. Manage Categories
                5. Manage Budgets
                6. Show Reports
                7. Exit
                """);
    }

    private static void addExpenseFlow() {
        System.out.println("-- Add Expense --");
        String dateStr = Utils.readString("Date (yyyy-MM-dd) [leave blank for today]: ", SC);
        LocalDate date = dateStr.isBlank() ? LocalDate.now() : Utils.parseDateOrToday(dateStr);
        double amount = Utils.readDouble("Amount: ", SC);
        String category = Utils.readString("Category: ", SC);
        if (!manager.categoryExists(category)) {
            System.out.printf("Category '%s' does not exist.\n", category);
            String create = Utils.readString("Create it? (y/n): ", SC);
            if (create.equalsIgnoreCase("y")) {
                manager.addCategory(new Category(category));
                System.out.println("Category created.");
            } else {
                System.out.println("Aborted adding expense.");
                return;
            }
        }
        String desc = Utils.readString("Description (optional): ", SC);
        Expense e = manager.addExpense(date, amount, category, desc);
        if (e != null) {
            System.out.println("Expense added: " + e);
        } else {
            System.out.println("Failed to add expense.");
        }
    }

    private static void listExpensesFlow() {
        System.out.println("-- All Expenses --");
        List<Expense> list = manager.listExpenses();
        if (list.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static void deleteExpenseFlow() {
        System.out.println("-- Delete Expense --");
        String id = Utils.readString("Enter Expense ID to delete: ", SC);
        boolean removed = manager.deleteExpenseById(id);
        System.out.println(removed ? "Deleted." : "Expense not found.");
    }

    private static void manageCategoriesFlow() {
        boolean inner = true;
        while (inner) {
            System.out.println("""
                    -- Categories --
                    1. List Categories
                    2. Add Category
                    3. Delete Category
                    4. Back
                    """);
            int c = Utils.readInt("Choose: ", SC);
            switch (c) {
                case 1 -> {
                    List<Category> cats = manager.listCategories();
                    if (cats.isEmpty()) System.out.println("No categories.");
                    else cats.forEach(cat -> System.out.println("- " + cat.getName()));
                }
                case 2 -> {
                    String name = Utils.readString("Category name: ", SC);
                    if (manager.categoryExists(name)) System.out.println("Already exists.");
                    else {
                        manager.addCategory(new Category(name));
                        System.out.println("Added.");
                    }
                }
                case 3 -> {
                    String name = Utils.readString("Category name to delete: ", SC);
                    boolean ok = manager.deleteCategory(name);
                    System.out.println(ok ? "Deleted." : "Not found or in use.");
                }
                case 4 -> inner = false;
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void manageBudgetsFlow() {
        boolean inner = true;
        while (inner) {
            System.out.println("""
                    -- Budgets --
                    1. List Budgets
                    2. Set/Update Budget
                    3. Delete Budget
                    4. Back
                    """);
            int c = Utils.readInt("Choose: ", SC);
            switch (c) {
                case 1 -> {
                    List<Budget> bs = manager.listBudgets();
                    if (bs.isEmpty()) System.out.println("No budgets set.");
                    else bs.forEach(b -> System.out.printf("%s : %s\n", b.getCategory(), Utils.formatMoney(b.getLimit())));
                }
                case 2 -> {
                    String cat = Utils.readString("Category: ", SC);
                    if (!manager.categoryExists(cat)) {
                        System.out.println("Category doesn't exist. Create it first.");
                        break;
                    }
                    double limit = Utils.readDouble("Limit amount: ", SC);
                    manager.setBudget(cat, limit);
                    System.out.println("Budget set.");
                }
                case 3 -> {
                    String cat = Utils.readString("Category to delete budget for: ", SC);
                    boolean ok = manager.deleteBudget(cat);
                    System.out.println(ok ? "Budget deleted." : "No such budget.");
                }
                case 4 -> inner = false;
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void reportsFlow() {
        System.out.println("""
                -- Reports --
                1. Monthly Summary
                2. Category Summary (all-time)
                3. Back
                """);
        int c = Utils.readInt("Choose: ", SC);
        switch (c) {
            case 1 -> {
                String month = Utils.readString("Enter month (YYYY-MM) [enter for current month]: ", SC);
                String m = month.isBlank() ? Utils.currentYearMonth() : month;
                System.out.println(reports.monthlySummary(m));
            }
            case 2 -> {
                System.out.println(reports.categorySummary());
            }
            case 3 -> { /* back */ }
            default -> System.out.println("Invalid.");
        }
    }
}
