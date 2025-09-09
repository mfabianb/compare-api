package com.example.compareapi.port;

import com.example.compareapi.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product save(Product product);
    void deleteById(String id);
    void deleteAll();
}
