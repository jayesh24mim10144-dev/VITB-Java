package com.expensetracker.core;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.storage.Storage;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private final Storage storage;
    private final List<Expense> expenses = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Budget> budgets = new ArrayList<>();

    public ExpenseManager() {
        this.storage = new Storage(Paths.get(System.getProperty("user.home"), ".expense_tracker_data"));
    }

    public void loadAll() {
        storage.ensureFiles();
        expenses.clear();
        categories.clear();
        budgets.clear();
        expenses.addAll(storage.loadExpenses());
        categories.addAll(storage.loadCategories());
        budgets.addAll(storage.loadBudgets());

        if (categories.isEmpty()) {
            categories.add(new Category("Food"));
            categories.add(new Category("Transport"));
            categories.add(new Category("Shopping"));
            storage.saveCategories(categories);
        }
    }

    public Expense addExpense(LocalDate date, double amount, String category, String description) {
        if (amount <= 0) return null;
        Expense e = Expense.create(date, amount, category.trim(), description);
        expenses.add(e);
        storage.saveExpenses(expenses);
        return e;
    }

    public List<Expense> listExpenses() {
        return new ArrayList<>(expenses);
    }

    public boolean deleteExpenseById(String id) {
        Optional<Expense> found = expenses.stream().filter(e -> e.getId().equals(id)).findFirst();
        if (found.isPresent()) {
            expenses.remove(found.get());
            storage.saveExpenses(expenses);
            return true;
        }
        return false;
    }

    public boolean categoryExists(String name) {
        return categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(name.trim()));
    }

    public void addCategory(Category cat) {
        if (!categoryExists(cat.getName())) {
            categories.add(cat);
            storage.saveCategories(categories);
        }
    }

    public boolean deleteCategory(String name) {
        String n = name.trim();
        boolean inUse = expenses.stream().anyMatch(e -> e.getCategory().equalsIgnoreCase(n));
        if (inUse) return false;
        boolean removed = categories.removeIf(c -> c.getName().equalsIgnoreCase(n));
        if (removed) storage.saveCategories(categories);
        return removed;
    }

    public List<Category> listCategories() {
        return new ArrayList<>(categories);
    }

    public void setBudget(String category, double limit) {
        budgets.removeIf(b -> b.getCategory().equalsIgnoreCase(category));
        budgets.add(new Budget(category, limit));
        storage.saveBudgets(budgets);
    }

    public boolean deleteBudget(String category) {
        boolean removed = budgets.removeIf(b -> b.getCategory().equalsIgnoreCase(category));
        if (removed) storage.saveBudgets(budgets);
        return removed;
    }

    public List<Budget> listBudgets() {
        return new ArrayList<>(budgets);
    }

    public Map<String, Double> totalByCategoryForMonth(String yearMonth) {
        return expenses.stream()
                .filter(e -> e.getDate().toString().startsWith(yearMonth))
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)));
    }

    public Map<String, Double> totalByCategoryAllTime() {
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)));
    }

    public double totalForMonth(String yearMonth) {
        return expenses.stream()
                .filter(e -> e.getDate().toString().startsWith(yearMonth))
                .mapToDouble(Expense::getAmount).sum();
    }

    public Optional<Budget> findBudgetForCategory(String category) {
        return budgets.stream().filter(b -> b.getCategory().equalsIgnoreCase(category)).findFirst();
    }
}
