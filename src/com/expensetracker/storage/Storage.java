package com.expensetracker.storage;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path dir;

    public Storage(Path dir) {
        this.dir = dir;
    }

    private Path expensesFile() { return dir.resolve("expenses.csv"); }
    private Path categoriesFile() { return dir.resolve("categories.csv"); }
    private Path budgetsFile() { return dir.resolve("budgets.csv"); }

    public void ensureFiles() {
        try {
            if (!Files.exists(dir)) Files.createDirectories(dir);
            if (!Files.exists(expensesFile())) Files.writeString(expensesFile(), "id,date,amount,category,description\n");
            if (!Files.exists(categoriesFile())) Files.writeString(categoriesFile(), "name\n");
            if (!Files.exists(budgetsFile())) Files.writeString(budgetsFile(), "category,limit\n");
        } catch (IOException e) {
            System.err.println("Error creating data files: " + e.getMessage());
        }
    }

    public List<Expense> loadExpenses() {
        List<Expense> list = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(expensesFile())) {
            String line = r.readLine();
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = splitCsv(line,5);
                String id = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                double amount = Double.parseDouble(parts[2]);
                String category = parts[3];
                String desc = parts[4];
                list.add(new Expense(id, date, amount, category, desc));
            }
        } catch (IOException ex) {
            System.err.println("Failed to load expenses: " + ex.getMessage());
        }
        return list;
    }

    public void saveExpenses(List<Expense> expenses) {
        try (BufferedWriter w = Files.newBufferedWriter(expensesFile())) {
            w.write("id,date,amount,category,description\n");
            for (Expense e : expenses) {
                w.write(escapeCsv(e.getId()) + "," + e.getDate() + "," + e.getAmount() + "," +
                        escapeCsv(e.getCategory()) + "," + escapeCsv(e.getDescription()) + "\n");
            }
        } catch (IOException ex) {
            System.err.println("Failed to save expenses: " + ex.getMessage());
        }
    }

    public List<Category> loadCategories() {
        List<Category> list = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(categoriesFile())) {
            String line = r.readLine();
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                list.add(new Category(line.trim()));
            }
        } catch (IOException ex) {
            System.err.println("Failed to load categories: " + ex.getMessage());
        }
        return list;
    }

    public void saveCategories(List<Category> cats) {
        try (BufferedWriter w = Files.newBufferedWriter(categoriesFile())) {
            w.write("name\n");
            for (Category c : cats) w.write(c.getName() + "\n");
        } catch (IOException ex) {
            System.err.println("Failed to save categories: " + ex.getMessage());
        }
    }

    public List<Budget> loadBudgets() {
        List<Budget> list = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(budgetsFile())) {
            String line = r.readLine();
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = splitCsv(line,2);
                String category = parts[0];
                double limit = Double.parseDouble(parts[1]);
                list.add(new Budget(category, limit));
            }
        } catch (IOException ex) {
            System.err.println("Failed to load budgets: " + ex.getMessage());
        }
        return list;
    }

    public void saveBudgets(List<Budget> budgets) {
        try (BufferedWriter w = Files.newBufferedWriter(budgetsFile())) {
            w.write("category,limit\n");
            for (Budget b : budgets) w.write(escapeCsv(b.getCategory()) + "," + b.getLimit() + "\n");
        } catch (IOException ex) {
            System.err.println("Failed to save budgets: " + ex.getMessage());
        }
    }

    private static String escapeCsv(String s) {
        if (s == null) return "";
        return s.replace("\n", " ").replace(",", " ");
    }

    private static String[] splitCsv(String line, int expected) {
        String[] parts = line.split(",", expected);

        if (parts.length < expected) {
            String[] out = new String[expected];
            System.arraycopy(parts, 0, out, 0, parts.length);
            for (int i = parts.length; i < expected; i++) out[i] = "";
            return out;
        }
        return parts;
    }
}
