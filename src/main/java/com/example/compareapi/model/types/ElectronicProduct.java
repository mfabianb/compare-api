package com.example.compareapi.model.types;

import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;

import java.util.Map;

/**
 * Example subtype for electronics. Add electronics-specific behavior here.
 */
public class ElectronicProduct extends Product {
    public ElectronicProduct() { super(); }

    public ElectronicProduct(String id, ProductType type, String name, String description, double price, double rating,
                             Map<String,Object> specifications, String imageUrl) {
        super(id, type, name, description, price, rating, specifications, imageUrl);
    }

    // electronics specific methods could go here (warranty, sku parsing, etc.)
}
