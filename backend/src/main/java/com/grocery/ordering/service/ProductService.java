package com.grocery.ordering.service;

import com.grocery.ordering.dto.ProductDTO;
import com.grocery.ordering.entity.Product;
import com.grocery.ordering.repository.ProductRepository;
import com.grocery.ordering.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Product operations.
 * Handles business logic for product management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Service
@Transactional
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Register a new product.
     * 
     * @param productDTO the product data
     * @return ProductDTO of the registered product
     * @throws RuntimeException if registration fails
     */
    public ProductDTO registerProduct(ProductDTO productDTO) {
        logger.info("Registering new product: {}", productDTO.getProductName());

        // Validate input
        ValidationUtils.validateProduct(productDTO);

        // Check if product with same name already exists
        if (productRepository.findByProductNameAndIsActiveTrue(productDTO.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name '" + productDTO.getProductName() + "' already exists");
        }

        // Create new product
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImageUrl(productDTO.getImageUrl());

        Product savedProduct = productRepository.save(product);
        logger.info("Product registered successfully with ID: {}", savedProduct.getProductId());

        return convertToDTO(savedProduct);
    }

    /**
     * Update product details.
     * 
     * @param productId the product ID
     * @param productDTO the updated product data
     * @return updated ProductDTO
     * @throws RuntimeException if product not found or update fails
     */
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        logger.info("Updating product with ID: {}", productId);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Validate input
        ValidationUtils.validateProduct(productDTO);

        // Check if product name is being changed and if it already exists
        if (!product.getProductName().equals(productDTO.getProductName()) && 
            productRepository.findByProductNameAndIsActiveTrue(productDTO.getProductName()).isPresent()) {
            throw new RuntimeException("Product with name '" + productDTO.getProductName() + "' already exists");
        }

        // Update product details
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImageUrl(productDTO.getImageUrl());

        Product updatedProduct = productRepository.save(product);
        logger.info("Product updated successfully with ID: {}", updatedProduct.getProductId());

        return convertToDTO(updatedProduct);
    }

    /**
     * Delete product by ID.
     * 
     * @param productId the product ID
     * @throws RuntimeException if product not found
     */
    public void deleteProduct(Long productId) {
        logger.info("Deleting product with ID: {}", productId);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        product.setIsActive(false);
        productRepository.save(product);

        logger.info("Product deleted successfully with ID: {}", productId);
    }

    /**
     * Get product by ID.
     * 
     * @param productId the product ID
     * @return ProductDTO
     * @throws RuntimeException if product not found
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        return convertToDTO(product);
    }

    /**
     * Search products by name.
     * 
     * @param productName the product name to search for
     * @return list of ProductDTOs matching the search criteria
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProductsByName(String productName) {
        logger.info("Searching products by name: {}", productName);

        ValidationUtils.validateSearchTerm(productName);

        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName.trim());
        
        if (products.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Search products by name or category.
     * 
     * @param searchTerm the search term
     * @return list of ProductDTOs matching the search criteria
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String searchTerm) {
        logger.info("Searching products with term: {}", searchTerm);

        ValidationUtils.validateSearchTerm(searchTerm);

        List<Product> products = productRepository.searchProducts(searchTerm.trim());
        
        if (products.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get all active products.
     * 
     * @return list of all active ProductDTOs
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllActiveProducts() {
        List<Product> products = productRepository.findByIsActiveTrue();
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get products by category.
     * 
     * @param category the product category
     * @return list of ProductDTOs in the category
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(String category) {
        ValidationUtils.preventSQLInjection(category, "Category");
        
        List<Product> products = productRepository.findByCategoryAndIsActiveTrue(category);
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get products in stock.
     * 
     * @return list of ProductDTOs that are in stock
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getInStockProducts() {
        List<Product> products = productRepository.findInStockProducts();
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get all distinct categories.
     * 
     * @return list of distinct categories
     */
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return productRepository.findDistinctCategories();
    }

    /**
     * Update product quantity.
     * 
     * @param productId the product ID
     * @param quantity the new quantity
     * @throws RuntimeException if product not found or quantity invalid
     */
    public void updateProductQuantity(Long productId, Integer quantity) {
        logger.info("Updating quantity for product ID: {} to {}", productId, quantity);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        ValidationUtils.validateQuantity(quantity);

        product.setQuantity(quantity);
        productRepository.save(product);

        logger.info("Product quantity updated successfully for ID: {}", productId);
    }

    /**
     * Reserve product for customer.
     * 
     * @param productId the product ID
     * @param customerId the customer ID
     * @param quantity the quantity to reserve
     * @throws RuntimeException if product not found or insufficient quantity
     */
    public void reserveProduct(Long productId, Long customerId, Integer quantity) {
        logger.info("Reserving product ID: {} for customer ID: {}, quantity: {}", productId, customerId, quantity);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity available. Available: " + product.getQuantity());
        }

        product.setQuantity(product.getQuantity() - quantity);
        product.setIsReserved(true);
        product.setReservedBy(customerId);
        productRepository.save(product);

        logger.info("Product reserved successfully for customer ID: {}", customerId);
    }

    /**
     * Convert Product entity to ProductDTO.
     * 
     * @param product the product entity
     * @return ProductDTO
     */
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        dto.setIsReserved(product.getIsReserved());
        dto.setReservedBy(product.getReservedBy());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setIsActive(product.getIsActive());
        return dto;
    }
}
