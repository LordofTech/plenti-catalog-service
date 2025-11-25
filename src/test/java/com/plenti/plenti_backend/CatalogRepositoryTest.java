package com.plenti.catalog.repository;

import com.plenti.catalog.model.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CatalogRepositoryTest {

    @Autowired
    private CatalogRepository catalogRepository;

    private Catalog catalog;

    @BeforeEach
    public void setup() {
        catalog = new Catalog("Product1", 100);
        catalogRepository.save(catalog);
    }

    @Test
    public void testFindById() {
        // Act
        Catalog fetchedCatalog = catalogRepository.findById(catalog.getId()).orElse(null);

        // Assert
        assertNotNull(fetchedCatalog);
        assertEquals("Product1", fetchedCatalog.getName());
        assertEquals(100, fetchedCatalog.getPrice());
    }

    @Test
    public void testDeleteById() {
        // Act
        catalogRepository.deleteById(catalog.getId());
        Catalog fetchedCatalog = catalogRepository.findById(catalog.getId()).orElse(null);

        // Assert
        assertNull(fetchedCatalog);
    }

    @Test
    public void testExistsById() {
        // Act
        boolean exists = catalogRepository.existsById(catalog.getId());

        // Assert
        assertTrue(exists);
    }
}
