package com.example.compareapi.adapter;

import com.example.compareapi.model.*;
import com.example.compareapi.model.enums.ProductType;
import com.example.compareapi.model.types.*;
import com.example.compareapi.port.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * A thread-safe file-backed repository. Uses Jackson to read/write a JSON array of products.
 * This implements the Outbound Adapter in Hexagonal Architecture.
 */
@Repository
public class FileJsonProductRepository implements ProductRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Value("${compare.data-file}")
    private String dataFilePath;

    private File dataFile;
    private List<Product> cache = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        // Resolve relative to classpath resources if possible
        dataFile = new File(getClass().getClassLoader().getResource(dataFilePath).getFile());
        if (!dataFile.exists()) {
            Files.createDirectories(dataFile.getParentFile().toPath());
            mapper.writeValue(dataFile, Collections.emptyList());
        }
        reload();
    }

    private void reload() throws IOException {
        lock.writeLock().lock();
        try {
            List<Map<String,Object>> raw = mapper.readValue(dataFile, new TypeReference<List<Map<String,Object>>>(){});
            cache = raw.stream().map(FileJsonProductRepository::toProduct).collect(Collectors.toList());
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static Product toProduct(Map<String,Object> m) {
        String type = (String) m.getOrDefault("type","OTHER");
        ProductType t = ProductType.valueOf(type);
        String id = (String) m.get("id");
        String name = (String) m.get("name");
        String description = (String) m.get("description");
        double price = ((Number)m.getOrDefault("price",0)).doubleValue();
        double rating = ((Number)m.getOrDefault("rating",0)).doubleValue();
        Map<String,Object> specs = (Map<String,Object>) m.getOrDefault("specifications", Collections.emptyMap());
        String imageUrl = (String) m.getOrDefault("imageUrl", null);

        switch (t) {
            case ELECTRONIC:
                return new ElectronicProduct(id, t, name, description, price, rating, specs, imageUrl);
            case CLOTHING:
                return new ClothingProduct(id, t, name, description, price, rating, specs, imageUrl);
            case BOOK:
                return new BookProduct(id, t, name, description, price, rating, specs, imageUrl);
            case FURNITURE:
                return new FurnitureProduct(id, t, name, description, price, rating, specs, imageUrl);
            default:
                return new OtherProduct(id, t, name, description, price, rating, specs, imageUrl);
        }
    }

    private void persist() {
        lock.readLock().lock();
        try {
            List<Map<String,Object>> serial = cache.stream().map(p -> {
                Map<String,Object> m = new LinkedHashMap<>();
                m.put("id", p.getId());
                m.put("type", p.getType() != null ? p.getType().name() : ProductType.OTHER.name());
                m.put("name", p.getName());
                m.put("description", p.getDescription());
                m.put("price", p.getPrice());
                m.put("rating", p.getRating());
                m.put("specifications", p.getSpecifications());
                m.put("imageUrl", p.getImageUrl());
                return m;
            }).collect(Collectors.toList());
            mapper.writerWithDefaultPrettyPrinter().writeValue(dataFile, serial);
        } catch (IOException e) {
            throw new RuntimeException("Unable to persist products file", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findAll() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(cache);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        lock.readLock().lock();
        try {
            return cache.stream().filter(p -> p.getId().equals(id)).findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Product save(Product product) {
        lock.writeLock().lock();
        try {
            cache.removeIf(p -> p.getId().equals(product.getId()));
            cache.add(product);
            persist();
            return product;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void deleteById(String id) {
        lock.writeLock().lock();
        try {
            cache.removeIf(p -> p.getId().equals(id));
            persist();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void deleteAll() {
        lock.writeLock().lock();
        try {
            cache.clear();
            persist();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
