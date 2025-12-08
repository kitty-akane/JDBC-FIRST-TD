package com.jdbc;

import java.time.Instant;

public class Product {

    private int id;
    private String name;
    private double price;
    private Instant creationDatetime;

    public Product(int id, String name, double price, Instant creationDatetime, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDatetime = creationDatetime;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price
                + ", creation=" + creationDatetime + "}";
    }
}
