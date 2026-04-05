package com.nexbuy.backend.repository;

import com.nexbuy.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);
    List<Product> findByCategoryAndSubCategory(String category, String subCategory);
    List<Product> findByNexPlusTrue();
    List<Product> findByPriceLessThan(double price);
    List<Product> findByRatingGreaterThanEqual(double rating);
    List<Product> findByDealPercentGreaterThan(int dealPercent);
    List<Product> findAllByOrderByDealPercentDesc();
    List<Product> findByStockStatus(String stockStatus);
    List<Product> findByBrand(String brand);

    // Full-text style search across name, brand, description, tags
    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrTagsContainingIgnoreCase(
        String name, String brand, String tags);

    // NEW: Price range filter
    List<Product> findByPriceBetween(double min, double max);

    // NEW: Category + price range
    List<Product> findByCategoryAndPriceBetween(String category, double min, double max);

    // NEW: Trending - highest rated + most reviewed
    @Query("SELECT p FROM Product p ORDER BY (p.rating * 0.6 + (p.reviewCount / 1000.0) * 0.4) DESC")
    List<Product> findTrending();

    // NEW: Similar products by category, excluding current
    @Query("SELECT p FROM Product p WHERE p.category = :cat AND p.id != :id ORDER BY p.rating DESC")
    List<Product> findSimilar(@Param("cat") String category, @Param("id") Long id);
}
