package com.example.compareapi.model.types;

import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;

import java.util.Map;

public class FurnitureProduct extends Product {

    public FurnitureProduct() { super(); }

    public FurnitureProduct(String id, ProductType type, String name, String description, double price, double rating,
                             Map<String,Object> specifications, String imageUrl) {
        super(id, type, name, description, price, rating, specifications, imageUrl);
    }

}
