package com.plenti.catalog.service;

import com.plenti.catalog.model.Catalog;
import com.plenti.catalog.repository.CatalogRepository;
import com.plenti.catalog.dto.CatalogDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CatalogServiceTest {

    @Mock
    private CatalogRepository catalogRepository;

    @InjectMocks
    private CatalogService catalogService;

    private Catalog catalog;
    private CatalogDTO catalogDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        catalogDTO = new CatalogDTO("Product1", 100);
        catalog = new Catalog("Product1", 100);
    }

    @Test
    public void testAddCatalog() {
        // Arrange
        when(catalogRepository.save(any(Catalog.class))).thenReturn(catalog);

        // Act
        Catalog addedCatalog = catalogService.addCatalog(catalogDTO);

        // Assert
        assertNotNull(addedCatalog);
        assertEquals("Product1", addedCatalog.getName());
        assertEquals(100, addedCatalog.getPrice());
        verify(catalogRepository, times(1)).save(any(Catalog.class));
    }

    @Test
    public void testGetCatalogById() {
        // Arrange
        when(catalogRepository.findById(1L)).thenReturn(java.util.Optional.of(catalog));

        // Act
        Catalog fetchedCatalog = catalogService.getCatalogById(1L);

        // Assert
        assertNotNull(fetchedCatalog);
        assertEquals("Product1", fetchedCatalog.getName());
        assertEquals(100, fetchedCatalog.getPrice());
        verify(catalogRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteCatalog() {
        // Arrange
        when(catalogRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = catalogService.deleteCatalog(1L);

        // Assert
        assertTrue(result);
        verify(catalogRepository, times(1)).deleteById(1L);
    }
}
