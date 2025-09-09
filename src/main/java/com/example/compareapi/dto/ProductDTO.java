package com.example.compareapi.dto;

import java.util.Map;

/**
 * Product DTO: resource representation used in the REST API responses.
 * This class is intentionally simple and serializable to JSON.
 */
public class ProductDTO {
    private String id;
    private String type;
    private String name;
    private String description;
    private double price;
    private double rating;
    //Set any number of specifications for products
    private Map<String, Object> specifications;
    private String imageUrl;

    public ProductDTO() {}

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

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
}
