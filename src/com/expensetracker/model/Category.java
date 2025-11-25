package com.expensetracker.model;

public class Category {
    private final String name;
    public Category(String name) { this.name = name.trim(); }
    public String getName() { return name; }
}
