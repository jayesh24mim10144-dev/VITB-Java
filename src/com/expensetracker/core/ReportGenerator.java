package com.expensetracker.core;

import com.expensetracker.model.Budget;
import com.expensetracker.util.Utils;

import java.util.Map;
import java.util.StringJoiner;

public class ReportGenerator {
    private final ExpenseManager manager;

    public ReportGenerator(ExpenseManager manager) {
        this.manager = manager;
    }

    public String monthlySummary(String yearMonth) {
        StringJoiner out = new StringJoiner("\n");
        out.add("Monthly Summary for: " + yearMonth);
        double total = manager.totalForMonth(yearMonth);
        out.add("Total spent: " + Utils.formatMoney(total));
        out.add("Breakdown by category:");
        Map<String, Double> byCat = manager.totalByCategoryForMonth(yearMonth);
        if (byCat.isEmpty()) out.add("  (no expenses)");
        else {
            for (Map.Entry<String, Double> e : byCat.entrySet()) {
                String cat = e.getKey();
                double amt = e.getValue();
                String line = String.format("  %s : %s", cat, Utils.formatMoney(amt));
                manager.findBudgetForCategory(cat).ifPresent(b -> {
                    double limit = b.getLimit();
                    String warn = amt > limit ? " (OVER BUDGET!)" : String.format(" (%.0f%% of budget)", (amt / limit) * 100);
                });
                out.add(line);
            }
        }
        out.add("\nBudgets:");
        for (Budget b : manager.listBudgets()) {
            out.add("  " + b.getCategory() + " : " + Utils.formatMoney(b.getLimit()));
        }
        return out.toString();
    }

    public String categorySummary() {
        StringJoiner out = new StringJoiner("\n");
        out.add("Category Summary (all time):");
        Map<String, Double> byCat = manager.totalByCategoryAllTime();
        if (byCat.isEmpty()) out.add("  (no expenses)");
        else byCat.forEach((k, v) -> out.add("  " + k + " : " + Utils.formatMoney(v)));
        return out.toString();
    }
}
