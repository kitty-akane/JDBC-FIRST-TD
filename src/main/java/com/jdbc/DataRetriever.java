package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection db;

    public DataRetriever() {
        this.db = new DBConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT id, name FROM product_category ORDER BY id";

        try (Connection conn = db.getDBConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot retrieve categories: " + e.getMessage());
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be positive.");
        }

        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql = """
                SELECT p.id, p.name, p.price, p.creation_datetime,
                       c.id AS category_id, c.name AS category_name
                FROM product p
                JOIN product_category c ON p.id = c.product_id
                ORDER BY p.id
                LIMIT ? OFFSET ?
                """;

        try (Connection conn = db.getDBConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(product);
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot retrieve products: " + e.getMessage());
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) {
        List<Product> products = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String baseSql = """
                SELECT p.id, p.name, p.price, p.creation_datetime,
                       c.id AS category_id, c.name AS category_name
                FROM product p
                JOIN product_category c ON p.id = c.product_id
                """;

        if (productName != null && !productName.isBlank()) {
            conditions.add("p.name ILIKE ?");
            params.add("%" + productName + "%");
        }

        if (categoryName != null && !categoryName.isBlank()) {
            conditions.add("c.name ILIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            conditions.add("p.creation_datetime >= ?");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            conditions.add("p.creation_datetime <= ?");
            params.add(Timestamp.from(creationMax));
        }

        String sql = baseSql;
        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        sql += " ORDER BY p.id";

        try (Connection conn = db.getDBConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(product);
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot filter products: " + e.getMessage());
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size
    ) {

        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be positive.");
        }

        List<Product> filtered = getProductsByCriteria(
                productName, categoryName, creationMin, creationMax
        );

        int from = (page - 1) * size;
        int to = Math.min(from + size, filtered.size());

        if (from >= filtered.size()) {
            return new ArrayList<>();
        }

        return filtered.subList(from, to);
    }
}
