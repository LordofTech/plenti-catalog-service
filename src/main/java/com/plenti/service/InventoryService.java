package com.plenti.catalog.service;

import com.plenti.catalog.model.InventoryBatch;
import com.plenti.catalog.model.Product;
import com.plenti.catalog.repository.InventoryRepository;
import com.plenti.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository,
                            ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Register an incoming stock batch for a product and update product stock.
     */
    @Transactional
    public InventoryBatch addStock(Long productId, int quantity, BigDecimal costPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        InventoryBatch batch = new InventoryBatch(product, quantity, costPrice);
        inventoryRepository.save(batch);

        // update product stock
        int newStock = product.getStock() + quantity;
        product.setStock(newStock);
        productRepository.save(product);

        return batch;
    }

    /**
     * Reduce stock for a product (e.g. when an order is placed).
     */
    @Transactional
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        int currentStock = product.getStock();
        if (quantity > currentStock) {
            throw new IllegalStateException("Insufficient stock for product: " + product.getName());
        }

        product.setStock(currentStock - quantity);
        productRepository.save(product);
    }

    /**
     * Recalculate product stock based on all inventory batches.
     * Useful if batch data changed and you want stock to be fully consistent.
     */
    @Transactional
    public void recalculateStockFromBatches(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        List<InventoryBatch> batches = inventoryRepository.findByProduct(product);
        int total = batches.stream()
                .mapToInt(InventoryBatch::getQuantity)
                .sum();

        product.setStock(total);
        productRepository.save(product);
    }

    /**
     * Get current stock for a product.
     */
    @Transactional(readOnly = true)
    public int getProductStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return product.getStock();
    }

    /**
     * Get all products that are below their reorder threshold or min stock level.
     * This is what your previous "low stock" logic was trying to do.
     */
    @Transactional(readOnly = true)
    public List<Product> getLowStockProducts() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> {
                    Integer reorderThreshold = product.getReorderThreshold();
                    Integer minStockLevel = product.getMinStockLevel();
                    int stock = product.getStock();

                    boolean belowReorder = (reorderThreshold != null) && (stock < reorderThreshold);
                    boolean belowMin = (minStockLevel != null) && (stock < minStockLevel);

                    return belowReorder || belowMin;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get all products with stock == 0.
     */
    @Transactional(readOnly = true)
    public List<Product> getOutOfStockProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .filter(p -> p.getStock() <= 0)
                .collect(Collectors.toList());
    }
}
