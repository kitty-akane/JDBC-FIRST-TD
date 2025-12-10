package com.jdbc;

import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataRetriever retriever = new DataRetriever();

        try {
            System.out.println("== Toutes les Categories ==");
            List<Category> categories = retriever.getAllCategories();
            for (Category c : categories) {
                System.out.println(c.getId() + " - " + c.getName());
            }
            System.out.println();

            System.out.println("=== getProductList(int page, int size) ===");
            testPagination(retriever, 1, 10);
            testPagination(retriever, 1, 5);
            testPagination(retriever, 1, 3);
            testPagination(retriever, 2, 2);

            System.out.println("=== getProductsByCriteria(...) - filtres seuls ===");
            testCriteria(retriever, "Dell", null, null, null);
            testCriteria(retriever, null, "info", null, null);
            testCriteria(retriever, "iPhone", "mobile", null, null);
            testCriteria(retriever, null, null,
                    Instant.parse("2024-02-01T00:00:00Z"),
                    Instant.parse("2024-03-01T23:59:59Z"));
            testCriteria(retriever, "Samsung", "bureau", null, null);
            testCriteria(retriever, "Sony", "informatique", null, null);
            testCriteria(retriever, null, "audio",
                    Instant.parse("2024-01-01T00:00:00Z"),
                    Instant.parse("2024-12-01T23:59:59Z"));
            testCriteria(retriever, null, null, null, null);

            System.out.println("=== 4. getProductsByCriteria(...) avec pagination ===");
            testCriteriaWithPage(retriever, null, null, null, null, 1, 10);
            testCriteriaWithPage(retriever, "Dell", null, null, null, 1, 5);
            testCriteriaWithPage(retriever, null, "informatique", null, null, 1, 10);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void testPagination(DataRetriever r, int page, int size) throws Exception {
        System.out.printf("--- Page %d, Size %d ---%n", page, size);
        List<Product> products = r.getProductList(page, size);
        for (Product p : products) {
            System.out.printf("ID: %d | %s | %.2f € | %s | Categories: %s%n",
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getCreationDatetime(),
                    p.getCategoryName() != null ? p.getCategoryName() : "(none)");
        }
        System.out.println();
    }

    private static void testCriteria(DataRetriever r,
            String productName,
            String categoryName,
            Instant min,
            Instant max) throws Exception {

        System.out.printf("--- Recherche : produit='%s', catégorie='%s', du %s au %s ---%n",
                productName != null ? productName : "(all)",
                categoryName != null ? categoryName : "(all)",
                min != null ? min : "(start)",
                max != null ? max : "(end)");

        List<Product> products = r.getProductsByCriteria(productName, categoryName, min, max);
        if (products.isEmpty()) {
            System.out.println("No product found.");
        } else {
            for (Product p : products) {
                System.out.printf("ID: %d | %s | %.2f € | %s | Categories: %s%n",
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getCreationDatetime(),
                        p.getCategoryName() != null ? p.getCategoryName() : "(none)");
            }
            System.out.println();
        }
    }
    

    

    private static void testCriteriaWithPage(DataRetriever r,
            String productName,
            String categoryName,
            Instant min,
            Instant max,
            int page,
            int size) throws Exception {

        System.out.printf("=== Recherche + Pagination : page=%d, size=%d ===%n", page, size);
        List<Product> products = r.getProductsByCriteria(productName, categoryName, min, max, page, size);
        if (products.isEmpty()) {
            System.out.println("no page was found.");
        } else {
            for (Product p : products) {
                System.out.printf("ID: %d | %s | %.2f € | %s | Categories: %s%n",
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getCreationDatetime(),
                        p.getCategoryName() != null ? p.getCategoryName() : "(none)");
            }
        }
        System.out.println();
    }
}
