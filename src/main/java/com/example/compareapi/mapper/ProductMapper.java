package com.example.compareapi.mapper;

import com.example.compareapi.dto.ProductDTO;
import com.example.compareapi.model.Product;

import java.util.List;

/**
 * Simple mapper between domain Product and ProductDTO.
 * In larger projects consider MapStruct.
 */
public class ProductMapper {
    public static ProductDTO toDto(Product p) {
        if (p == null) return null;
        ProductDTO d = new ProductDTO();
        d.setId(p.getId());
        d.setType(p.getType() != null ? p.getType().name() : null);
        d.setName(p.getName());
        d.setDescription(p.getDescription());
        d.setPrice(p.getPrice());
        d.setRating(p.getRating());
        d.setSpecifications(p.getSpecifications());
        d.setImageUrl(p.getImageUrl());
        return d;
    }

    public static ProductDTO filterFields(ProductDTO dto, List<String> fields) {
        if (fields == null || fields.isEmpty()) {
            return dto; // return as-is
        }

        ProductDTO filtered = new ProductDTO();

        if (fields.contains("id")) filtered.setId(dto.getId());
        if (fields.contains("name")) filtered.setName(dto.getName());
        if (fields.contains("description")) filtered.setDescription(dto.getDescription());
        if (fields.contains("price")) filtered.setPrice(dto.getPrice());
        if (fields.contains("rating")) filtered.setRating(dto.getRating());
        if (fields.contains("specifications")) filtered.setSpecifications(dto.getSpecifications());
        if (fields.contains("imageUrl")) filtered.setImageUrl(dto.getImageUrl());
        if (fields.contains("type")) filtered.setType(dto.getType());

        return filtered;
    }
}
