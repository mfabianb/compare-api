package com.example.compareapi.model;

import com.example.compareapi.model.enums.ProductType;

import java.util.Map;
import java.util.Objects;

/**
 * Abstract base Product class. New product categories should extend this class
 * to take advantage of inheritance and polymorphism.
 */
public abstract class Product {
    private String id;
    private ProductType type;
    private String name;
    private String description;
    private double price;
    private double rating;
    private Map<String, Object> specifications;
    private String imageUrl;

    public Product() {}

    public Product(String id, ProductType type, String name, String description, double price, double rating,
                   Map<String, Object> specifications, String imageUrl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.specifications = specifications;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ProductType getType() { return type; }
    public void setType(ProductType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public Map<String, Object> getSpecifications() { return specifications; }
    public void setSpecifications(Map<String, Object> specifications) { this.specifications = specifications; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
