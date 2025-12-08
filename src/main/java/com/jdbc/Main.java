package com.jdbc;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        DataRetriever dr = new DataRetriever();

        System.out.println("------ ALL CATEGORIES ------");
        System.out.println(dr.getAllCategories());

        System.out.println("------ PAGE 1 / SIZE 2 ------");
        System.out.println(dr.getProductList(1, 2));

        System.out.println("------ FILTER: name contains 'Lap' ------");
        System.out.println(dr.getProductsByCriteria("Lap", null, null, null));

        System.out.println("------ FILTER + PAGINATION ------");
        System.out.println(dr.getProductsByCriteria(
                null,
                "Laptop",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2025-01-01T00:00:00Z"),
                1,
                1
        ));
    }
}