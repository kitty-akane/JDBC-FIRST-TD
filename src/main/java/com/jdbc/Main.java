package com.jdbc;

import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataRetriever dr = new DataRetriever();

        title("ALL CATEGORIES");
        printCategories(dr.getAllCategories());

        title("PAGE 1 — SIZE 2");
        printProducts(dr.getProductList(1, 2));

        title("FILTER: name contains 'Mac'");
        printProducts(dr.getProductsByCriteria("Mac", null, null, null));

        title("FILTER + PAGINATION");
        printProducts(
                dr.getProductsByCriteria(
                        null,
                        "Laptop",
                        Instant.parse("2024-01-01T00:00:00Z"),
                        Instant.parse("2025-01-01T00:00:00Z"),
                        1,
                        1
                )
        );
    }

    private static void title(String t) {
        System.out.println("\n=== " + t + " ===");
    }

    private static void printCategories(List<Category> categories) {
        for (Category c : categories) {
            System.out.println(c.getId() + " - " + c.getName());
        }
        System.out.println("Total: " + categories.size());
    }

    private static void printProducts(List<Product> products) {
        for (Product p : products) {
            System.out.println(
                    p.getId() + " | " +
                    p.getName() + " | " +
                    p.getPrice() + " | " +
                    p.getCategoryName() + " | " +
                    p.getCreationDatetime()
            );
        }
        System.out.println("Total: " + products.size());
    }
}
