package com.plenti.catalog.controller;

import com.plenti.catalog.service.CatalogService;
import com.plenti.catalog.model.Catalog;
import com.plenti.catalog.dto.CatalogDTO;
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

public class CatalogControllerTest {

    @Mock
    private CatalogService catalogService;

    @InjectMocks
    private CatalogController catalogController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(catalogController).build();
    }

    @Test
    public void testCreateCatalog() throws Exception {
        // Arrange
        CatalogDTO catalogDTO = new CatalogDTO("Product1", 100);
        Catalog catalog = new Catalog("Product1", 100);
        when(catalogService.addCatalog(any(CatalogDTO.class))).thenReturn(catalog);

        // Act & Assert
        mockMvc.perform(post("/api/catalog")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Product1\", \"price\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.price").value(100));

        verify(catalogService, times(1)).addCatalog(any(CatalogDTO.class));
    }

    @Test
    public void testGetCatalogById() throws Exception {
        // Arrange
        Catalog catalog = new Catalog("Product1", 100);
        when(catalogService.getCatalogById(1L)).thenReturn(catalog);

        // Act & Assert
        mockMvc.perform(get("/api/catalog/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.price").value(100));

        verify(catalogService, times(1)).getCatalogById(1L);
    }

    @Test
    public void testDeleteCatalog() throws Exception {
        // Arrange
        when(catalogService.deleteCatalog(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/catalog/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(catalogService, times(1)).deleteCatalog(1L);
    }
}
