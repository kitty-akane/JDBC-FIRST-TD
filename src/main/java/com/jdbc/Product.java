package com.jdbc;

import java.time.Instant;

public class Product {

    private int id;
    private String name;
    private double price;
    private Instant creationDatetime;
    private Category category;

    public Product() {
    }

    public Product(int id, String name, double price, Instant creationDatetime, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDatetime = creationDatetime;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return (category != null) ? category.getName() : null;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", creationDatetime=" + creationDatetime
                + ",category=" + category
                + '}';
    }
}
