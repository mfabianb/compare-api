package com.example.compareapi.controller;

import com.example.compareapi.dto.ProductDTO;
import com.example.compareapi.mapper.ProductMapper;
import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;
import com.example.compareapi.model.types.ElectronicProduct;
import com.example.compareapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing products.
 * Implements CRUD, pagination, filtering, and error handling.
 * returns JSON DTOs.
 */
@RestController
@RequestMapping(path = "/api/v1/products", produces = "application/json")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * GET /api/v1/products
     * Supports pagination: page (0-based) and size
     * Supports filtering by type and name (partial)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam Optional<String> type,
            @RequestParam Optional<String> name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<Product> items = service.findAll(type, name, page, size);
        List<ProductDTO> dtos = items.stream()
                .map(ProductMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("size", size);
        response.put("count", dtos.size());
        response.put("products", dtos);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/v1/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id) {
        return service.findById(id)
                .map(p -> ResponseEntity.ok(ProductMapper.toDto(p)))
                .orElseThrow();
    }

    /**
     * POST /api/v1/products
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductDTO> create(@Validated @RequestBody ProductDTO dto) {
        Product p = new ElectronicProduct(
                dto.getId(),
                dto.getType() != null ? ProductType.valueOf(dto.getType()) : ProductType.OTHER,
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getRating(),
                dto.getSpecifications(),
                dto.getImageUrl()
        );
        Product created = null;
        created = service.create(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDto(created));
    }

    /**
     * PUT /api/v1/products/{id} - full update
     */
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @Validated @RequestBody ProductDTO dto) {
        Product p = new ElectronicProduct(
                id,
                dto.getType() != null ? ProductType.valueOf(dto.getType()) : ProductType.OTHER,
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getRating(),
                dto.getSpecifications(),
                dto.getImageUrl()
        );
        return service.update(id, p)
                .map(updated -> ResponseEntity.ok(ProductMapper.toDto(updated)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * PATCH /api/v1/products/{id} - partial update
     */
    @PatchMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<ProductDTO> patch(@PathVariable String id, @RequestBody ProductDTO dto) {
        Optional<Product> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Product p = existing.get();
        if (dto.getName() != null) p.setName(dto.getName());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getPrice() != 0) p.setPrice(dto.getPrice());
        if (dto.getRating() != 0) p.setRating(dto.getRating());
        if (dto.getSpecifications() != null) p.setSpecifications(dto.getSpecifications());
        if (dto.getImageUrl() != null) p.setImageUrl(dto.getImageUrl());

        service.create(p); // save updated
        return ResponseEntity.ok(ProductMapper.toDto(p));
    }

    /**
     * DELETE /api/v1/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        boolean deleted = service.deleteById(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * GET /api/v1/products/compare
     */
    @GetMapping("/compare")
    public ResponseEntity<List<ProductDTO>> compareProducts(
            @RequestParam List<String> ids,
            @RequestParam(required = false) List<String> fields) {

        List<ProductDTO> products = service.compareProductsById(ids);

        if (fields != null && !fields.isEmpty()) {
            products = products.stream()
                    .map(p -> ProductMapper.filterFields(p, fields))
                    .toList();
        }

        return ResponseEntity.ok(products);
    }
}
