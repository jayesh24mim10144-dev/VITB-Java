package com.expensetracker.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Expense {
    private final String id;
    private final LocalDate date;
    private final double amount;
    private final String category;
    private final String description;

    public Expense(String id, LocalDate date, double amount, String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description == null ? "" : description;
    }

    public static Expense create(LocalDate date, double amount, String category, String description) {
        return new Expense(UUID.randomUUID().toString(), date, amount, category, description);
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s",
                id, date, String.format("%.2f", amount), category, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
