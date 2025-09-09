package com.example.compareapi.service;

import com.example.compareapi.exception.BadFormatException;
import com.example.compareapi.exception.NotFoundException;
import com.example.compareapi.model.types.ElectronicProduct;
import com.example.compareapi.model.Product;
import com.example.compareapi.model.enums.ProductType;
import com.example.compareapi.port.ProductRepository;
import com.example.compareapi.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new ProductServiceImpl(repository);
    }

    @Test
    public void testFindAllPaginationAndFilter() {
        Product p1 = createProductObject();
        Product p2 = createProductObject();
        p2.setName("Laptop");

        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> page0 = service.findAll(Optional.of("ELECTRONIC"), Optional.empty(), 0, 1);
        assertEquals(1, page0.size());
        assertEquals("Phone", page0.get(0).getName());

        List<Product> page1 = service.findAll(Optional.of("ELECTRONIC"), Optional.empty(), 1, 1);
        assertEquals(1, page1.size());
        assertEquals("Laptop", page1.get(0).getName());
    }

    @Test
    public void testCreateAndFindById() {
        Product p = createProductObject();
        when(repository.save(any())).thenAnswer(inv -> {
            Product arg = inv.getArgument(0);
            arg.setId("7945cc8c-bed2-4e9b-94f8-0c9319427b96");
            return arg;
        });
        Product created = null;
        created = service.create(p);
        assertNotNull(created.getId());
    }

    @Test
    void testGetProductById_found() {
        Product product = createProductObject();
        when(repository.findById("7945cc8c-bed2-4e9b-94f8-0c9319427b96")).thenReturn(Optional.of(product));

        Optional<Product> dto = service.findById("7945cc8c-bed2-4e9b-94f8-0c9319427b96");

        assertNotNull(dto);
        assertEquals("Phone", dto.get().getName());
        verify(repository, times(1)).findById(anyString());
    }

    @Test
    void testGetProductById_notFound() {
        when(repository.findById("7945cc8c-bed2-4e9b-94f8-0c9319427b96")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById("7945cc8c-bed2-4e9b-94f8-0c9319427b96"));
    }

    @Test
    void testCreateProduct() {
        Product product = createProductObject();
        product.setName("Book");

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        Product result = service.create(product);

        verify(repository).save(captor.capture());
        assertEquals("Book", captor.getValue().getName());
        //assertEquals("Book", result.getName());
    }

    @Test
    void testUpdateProduct() {
        Product existing = createProductObject();

        when(repository.findById("7945cc8c-bed2-4e9b-94f8-0c9319427b94")).thenReturn(Optional.of(existing));

        Product dto = createProductObject();

        service.update("7945cc8c-bed2-4e9b-94f8-0c9319427b94", dto);

    }

    @Test
    void testDeleteProduct() {

        Product existing = createProductObject();

        when(repository.findById("7945cc8c-bed2-4e9b-94f8-0c9319427b96")).thenReturn(Optional.of(existing));

        service.deleteById("7945cc8c-bed2-4e9b-94f8-0c9319427b96");

        verify(repository).deleteById("7945cc8c-bed2-4e9b-94f8-0c9319427b96");
    }

    private Product createProductObject(){
        return new ElectronicProduct("7945cc8c-bed2-4e9b-94f8-0c9319427b96", ProductType.ELECTRONIC, "Phone",
                "desc", 1000, 4, java.util.Collections.emptyMap(), "https://example.com/images/superphone.jpg");
    }

    /*@Test
    void testGetProductsByIds() {
        Product p1 = new Product("1", "Phone", "Smartphone", 499.99, 4.5,
                Map.of(), "url", ProductType.ELECTRONIC.name());
        Product p2 = new Product("2", "Book", "Good book", 19.99, 4.8,
                Map.of(), "url", ProductType.BOOK.name());

        when(repository.findById("1")).thenReturn(Optional.of(p1));
        when(repository.findById("2")).thenReturn(Optional.of(p2));

        List<ProductDTO> result = service.getProductsByIds(List.of("1", "2"));

        assertEquals(2, result.size());
        assertEquals("Phone", result.get(0).getName());
        assertEquals("Book", result.get(1).getName());
    }*/
}
