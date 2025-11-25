package com.plenti.repository;

import com.plenti.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product("Laptop", 50000);
        productRepository.save(product);
    }

    @Test
    public void testFindById() {
        // Act
        Product fetchedProduct = productRepository.findById(product.getId()).orElse(null);

        // Assert
        assertNotNull(fetchedProduct);
        assertEquals("Laptop", fetchedProduct.getName());
        assertEquals(50000, fetchedProduct.getPrice());
    }

    @Test
    public void testDeleteById() {
        // Act
        productRepository.deleteById(product.getId());
        Product fetchedProduct = productRepository.findById(product.getId()).orElse(null);

        // Assert
        assertNull(fetchedProduct);
    }

    @Test
    public void testExistsById() {
        // Act
        boolean exists = productRepository.existsById(product.getId());

        // Assert
        assertTrue(exists);
    }
}
