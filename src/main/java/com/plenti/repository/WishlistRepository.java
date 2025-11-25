package com.plenti.catalog.repository;

import com.plenti.catalog.model.Wishlist;
import com.plenti.catalog.model.Product;
import com.plenti.identity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    boolean existsByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);
}
