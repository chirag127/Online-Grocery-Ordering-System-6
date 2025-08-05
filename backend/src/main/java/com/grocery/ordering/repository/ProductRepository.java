package com.grocery.ordering.repository;

import com.grocery.ordering.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity operations.
 * Provides CRUD operations and custom queries for product management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find products by name (case-insensitive search).
     * 
     * @param productName the product name to search for
     * @return list of products matching the name
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%')) AND p.isActive = true")
    List<Product> findByProductNameContainingIgnoreCase(@Param("productName") String productName);

    /**
     * Find all active products.
     * 
     * @return list of active products
     */
    List<Product> findByIsActiveTrue();

    /**
     * Find products by category.
     * 
     * @param category the product category
     * @return list of products in the category
     */
    List<Product> findByCategoryAndIsActiveTrue(String category);

    /**
     * Find products with quantity greater than specified amount.
     * 
     * @param quantity the minimum quantity
     * @return list of products with sufficient quantity
     */
    @Query("SELECT p FROM Product p WHERE p.quantity > :quantity AND p.isActive = true")
    List<Product> findByQuantityGreaterThan(@Param("quantity") Integer quantity);

    /**
     * Find products within price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of products within the price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.isActive = true")
    List<Product> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Find products that are in stock (quantity > 0).
     * 
     * @return list of products in stock
     */
    @Query("SELECT p FROM Product p WHERE p.quantity > 0 AND p.isActive = true")
    List<Product> findInStockProducts();

    /**
     * Find products that are out of stock (quantity = 0).
     * 
     * @return list of products out of stock
     */
    @Query("SELECT p FROM Product p WHERE p.quantity = 0 AND p.isActive = true")
    List<Product> findOutOfStockProducts();

    /**
     * Find reserved products.
     * 
     * @return list of reserved products
     */
    List<Product> findByIsReservedTrueAndIsActiveTrue();

    /**
     * Find products reserved by a specific customer.
     * 
     * @param customerId the customer ID
     * @return list of products reserved by the customer
     */
    List<Product> findByReservedByAndIsActiveTrue(Long customerId);

    /**
     * Find all distinct categories.
     * 
     * @return list of distinct product categories
     */
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.isActive = true AND p.category IS NOT NULL")
    List<String> findDistinctCategories();

    /**
     * Count total number of active products.
     * 
     * @return count of active products
     */
    @Query("SELECT COUNT(p) FROM Product p WHERE p.isActive = true")
    long countActiveProducts();

    /**
     * Find products by exact name match.
     * 
     * @param productName the exact product name
     * @return Optional containing the product if found
     */
    Optional<Product> findByProductNameAndIsActiveTrue(String productName);

    /**
     * Search products by name or category (case-insensitive).
     * 
     * @param searchTerm the search term
     * @return list of products matching the search term
     */
    @Query("SELECT p FROM Product p WHERE (LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND p.isActive = true")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);
}
