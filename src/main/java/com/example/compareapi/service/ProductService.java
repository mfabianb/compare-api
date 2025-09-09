package com.example.compareapi.service;

import com.example.compareapi.dto.ProductDTO;
import com.example.compareapi.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll(Optional<String> typeFilter, Optional<String> nameFilter, int page, int size);
    Optional<Product> findById(String id);
    Product create(Product product);
    Optional<Product> update(String id, Product product);
    boolean deleteById(String id);
    List<ProductDTO> compareProductsById(List<String> ids);
}
