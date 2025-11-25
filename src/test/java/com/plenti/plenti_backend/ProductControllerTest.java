package com.plenti.controller;

import com.plenti.service.ProductService;
import com.plenti.model.Product;
import com.plenti.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Arrange
        ProductDTO productDTO = new ProductDTO("Laptop", 50000);
        Product product = new Product("Laptop", 50000);
        when(productService.addProduct(any(ProductDTO.class))).thenReturn(product);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Laptop\", \"price\":50000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(50000));

        verify(productService, times(1)).addProduct(any(ProductDTO.class));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Arrange
        Product product = new Product("Laptop", 50000);
        when(productService.getProductById(1L)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(50000));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}
