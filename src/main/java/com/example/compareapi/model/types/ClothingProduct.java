package com.example.compareapi.model.types;

import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;

import java.util.Map;

/**
 * Example subtype for clothing. Add clothing-specific behavior here.
 */
public class ClothingProduct extends Product {
    public ClothingProduct() { super(); }

    public ClothingProduct(String id, ProductType type, String name, String description, double price, double rating,
                           Map<String,Object> specifications, String imageUrl) {
        super(id, type, name, description, price, rating, specifications, imageUrl);
    }

    // clothing specific methods could go here (size normalization, etc.)
}
