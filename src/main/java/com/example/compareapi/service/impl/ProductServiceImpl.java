package com.example.compareapi.service.impl;

import com.example.compareapi.dto.ProductDTO;
import com.example.compareapi.exception.BadFormatException;
import com.example.compareapi.exception.NotFoundException;
import com.example.compareapi.mapper.ProductMapper;
import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;
import com.example.compareapi.port.ProductRepository;
import com.example.compareapi.service.ProductService;
import com.example.compareapi.validators.Validations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.compareapi.constants.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll(Optional<String> typeFilter, Optional<String> nameFilter, int page, int size) {
        List<Product> all = repository.findAll();

        // Filtering by type
        if (typeFilter.isPresent()) {
            try {
                ProductType t = ProductType.valueOf(typeFilter.get().toUpperCase());
                all = all.stream().filter(p -> p.getType() == t).collect(Collectors.toList());
            } catch (IllegalArgumentException ignored) {}
        }

        // Filtering by partial name
        if (nameFilter.isPresent()) {
            String nf = nameFilter.get().toLowerCase();
            all = all.stream().filter(p -> p.getName() != null && p.getName().toLowerCase().contains(nf)).collect(Collectors.toList());
        }

        // Pagination
        int from = Math.max(0, page * size);
        int to = Math.min(all.size(), from + size);
        if (from > to) return Collections.emptyList();
        return all.subList(from, to);
    }


    @Override
    public Optional<Product> findById(String id) {

        //Validate if id not matches format and terminate process with exception
        if(Objects.nonNull(id) && !Validations.validateRegexFormat(UUID_REGEX, id)){
            throw new BadFormatException(UUID_BAD_FORMAT);
        }

        //Validate if id matches format and search in storage, exception if resource not exists
        return Optional.ofNullable(repository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Product create(Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId(UUID.randomUUID().toString());
        }

        //Validate every field of product, if field is null is ok for optional, mandatory field have to be not null
        Validations.validateInputProduct(product);

        return repository.save(product);
    }

    @Override
    public Optional<Product> update(String id, Product product) {

        //Validate if id not matches format and terminate process with exception
        if(Objects.nonNull(id) && !Validations.validateRegexFormat(UUID_REGEX, id)){
            throw new BadFormatException(UUID_BAD_FORMAT);
        }

        //Validate if resource exists
        Optional<Product> existing = repository.findById(id);
        if (existing.isEmpty()) throw new NotFoundException();

        //Validate every field of product, if field is null is ok for optional, mandatory field have to be not null
        Validations.validateInputProduct(product);

        product.setId(id);
        repository.save(product);
        return Optional.of(product);
    }

    @Override
    public boolean deleteById(String id) {

        //Validate if id not matches format and terminate process with exception
        if(Objects.nonNull(id) && !Validations.validateRegexFormat(UUID_REGEX, id)){
            throw new BadFormatException(UUID_BAD_FORMAT);
        }

        //Validate if resource exists, delete if exists
        Optional<Product> existing = repository.findById(id);
        if (existing.isEmpty()) return false;
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<ProductDTO> compareProductsById(List<String> ids) {

        //Validate every id, if someone do not match format, then terminate process with exception
        for(String id: ids){
            if(Objects.nonNull(id) && !Validations.validateRegexFormat(UUID_REGEX, id)){
                throw new BadFormatException(UUID_BAD_FORMAT);
            }
        }

        /*
        * Convert ids to stream, for each id find in storage and map
        * filter only if is present
        * get optional and map
        * transform Product to ProductDto and map
        * transform stream to list again
        * */
        List<ProductDTO> list = ids.stream()
                .map(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductMapper::toDto)
                .toList();

        /*
        * Transform all product types from list to a new list
        * Collect list to set
        * Set only stores unique values, so, there is not repeated types
        * */
        Set<String> types = list.stream().map(ProductDTO::getType).collect(Collectors.toSet());

        /*
        * Validate if set size is bigger than 1, if is: there is more than one type in compare list
        * You can not compare different product types, must be the same type
        * */
        if(types.size() > 1){
            throw new BadFormatException(INVALID_COMPARISON);
        }

        return list;
    }
}
