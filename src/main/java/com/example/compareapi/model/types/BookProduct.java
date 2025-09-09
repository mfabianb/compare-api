package com.example.compareapi.model.types;

import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;

import java.util.Map;

public class BookProduct extends Product {

    public BookProduct() { super(); }

    public BookProduct(String id, ProductType type, String name, String description, double price, double rating,
                           Map<String,Object> specifications, String imageUrl) {
        super(id, type, name, description, price, rating, specifications, imageUrl);
    }
}
