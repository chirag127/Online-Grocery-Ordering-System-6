package com.grocery.ordering.repository;

import com.grocery.ordering.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Customer entity operations.
 * Provides CRUD operations and custom queries for customer management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find customer by email address.
     * 
     * @param email the email address
     * @return Optional containing the customer if found
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Find customer by email and active status.
     * 
     * @param email the email address
     * @param isActive the active status
     * @return Optional containing the customer if found
     */
    Optional<Customer> findByEmailAndIsActive(String email, Boolean isActive);

    /**
     * Check if customer exists by email.
     * 
     * @param email the email address
     * @return true if customer exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find customers by name (case-insensitive search).
     * 
     * @param customerName the customer name to search for
     * @return list of customers matching the name
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerName) LIKE LOWER(CONCAT('%', :customerName, '%')) AND c.isActive = true")
    List<Customer> findByCustomerNameContainingIgnoreCase(@Param("customerName") String customerName);

    /**
     * Find all active customers.
     * 
     * @return list of active customers
     */
    List<Customer> findByIsActiveTrue();

    /**
     * Find customers by contact number.
     * 
     * @param contactNumber the contact number
     * @return list of customers with the given contact number
     */
    List<Customer> findByContactNumber(String contactNumber);

    /**
     * Count total number of active customers.
     * 
     * @return count of active customers
     */
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.isActive = true")
    long countActiveCustomers();

    /**
     * Find customers who have placed orders.
     * 
     * @return list of customers with orders
     */
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.orders o WHERE c.isActive = true")
    List<Customer> findCustomersWithOrders();

    /**
     * Find customers by partial email match (case-insensitive).
     * 
     * @param email the email pattern to search for
     * @return list of customers matching the email pattern
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')) AND c.isActive = true")
    List<Customer> findByEmailContainingIgnoreCase(@Param("email") String email);
}
