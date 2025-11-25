package com.plenti.service;

import com.plenti.model.Product;
import com.plenti.repository.ProductRepository;
import com.plenti.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO("Laptop", 50000);
        Product product = new Product("Laptop", 50000);
        
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product addedProduct = productService.addProduct(productDTO);

        // Assert
        assertNotNull(addedProduct);
        assertEquals("Laptop", addedProduct.getName());
        assertEquals(50000, addedProduct.getPrice());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Product product = new Product("Laptop", 50000);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));

        // Act
        Product fetchedProduct = productService.getProductById(1L);

        // Assert
        assertNotNull(fetchedProduct);
        assertEquals("Laptop", fetchedProduct.getName());
        assertEquals(50000, fetchedProduct.getPrice());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        Product product = new Product("Laptop", 50000);
        when(productRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = productService.deleteProduct(1L);

        // Assert
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
