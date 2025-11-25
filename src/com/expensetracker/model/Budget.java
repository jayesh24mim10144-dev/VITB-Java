package com.expensetracker.model;

public class Budget {
    private final String category;
    private final double limit;

    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
    }

    public String getCategory() { return category; }
    public double getLimit() { return limit; }
}
