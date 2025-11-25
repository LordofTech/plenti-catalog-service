package com.plenti.catalog.repository;

import com.plenti.catalog.model.InventoryBatch;
import com.plenti.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {

    List<InventoryBatch> findByProduct(Product product);
}
