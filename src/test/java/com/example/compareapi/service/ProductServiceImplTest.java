package com.example.compareapi.service;

import com.example.compareapi.model.types.ElectronicProduct;
import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;
import com.example.compareapi.port.ProductRepository;
import com.example.compareapi.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    private ProductRepository repo;
    private ProductServiceImpl service;

    @BeforeEach
    public void setup() {
        repo = Mockito.mock(ProductRepository.class);
        service = new ProductServiceImpl(repo);
    }

    @Test
    public void testFindAllPaginationAndFilter() {
        Product p1 = new ElectronicProduct("1", ProductType.ELECTRONIC, "Phone", "desc", 100, 4.5, java.util.Collections.emptyMap(), null);
        Product p2 = new ElectronicProduct("2", ProductType.ELECTRONIC, "Laptop", "desc", 1000, 4.8, java.util.Collections.emptyMap(), null);
        when(repo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> page0 = service.findAll(Optional.of("ELECTRONIC"), Optional.empty(), 0, 1);
        assertEquals(1, page0.size());
        assertEquals("Phone", page0.get(0).getName());

        List<Product> page1 = service.findAll(Optional.of("ELECTRONIC"), Optional.empty(), 1, 1);
        assertEquals(1, page1.size());
        assertEquals("Laptop", page1.get(0).getName());
    }

    @Test
    public void testCreateAndFindById() {
        Product p = new ElectronicProduct(null, ProductType.ELECTRONIC, "NewPhone", "desc", 200, 4.0, java.util.Collections.emptyMap(), null);
        when(repo.save(any())).thenAnswer(inv -> {
            Product arg = inv.getArgument(0);
            arg.setId("generated-id");
            return arg;
        });
        Product created = null;
        created = service.create(p);
        assertNotNull(created.getId());
    }
}
