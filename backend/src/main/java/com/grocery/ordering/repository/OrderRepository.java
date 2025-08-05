package com.grocery.ordering.repository;

import com.grocery.ordering.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Order entity operations.
 * Provides CRUD operations and custom queries for order management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find orders by customer ID.
     * 
     * @param customerId the customer ID
     * @return list of orders for the customer
     */
    @Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId ORDER BY o.orderDate DESC")
    List<Order> findByCustomerCustomerId(@Param("customerId") Long customerId);

    /**
     * Find orders by customer ID with order items.
     * 
     * @param customerId the customer ID
     * @return list of orders with order items for the customer
     */
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product WHERE o.customer.customerId = :customerId ORDER BY o.orderDate DESC")
    List<Order> findByCustomerCustomerIdWithItems(@Param("customerId") Long customerId);

    /**
     * Find orders by status.
     * 
     * @param status the order status
     * @return list of orders with the specified status
     */
    List<Order> findByOrderStatus(Order.OrderStatus status);

    /**
     * Find orders within date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of orders within the date range
     */
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find recent orders (last 30 days).
     * 
     * @return list of recent orders
     */
    @Query("SELECT o FROM Order o WHERE o.orderDate >= :thirtyDaysAgo ORDER BY o.orderDate DESC")
    List<Order> findRecentOrders(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);

    /**
     * Find orders by customer and status.
     * 
     * @param customerId the customer ID
     * @param status the order status
     * @return list of orders matching the criteria
     */
    @Query("SELECT o FROM Order o WHERE o.customer.customerId = :customerId AND o.orderStatus = :status ORDER BY o.orderDate DESC")
    List<Order> findByCustomerCustomerIdAndOrderStatus(@Param("customerId") Long customerId, @Param("status") Order.OrderStatus status);

    /**
     * Count total orders.
     * 
     * @return total number of orders
     */
    @Query("SELECT COUNT(o) FROM Order o")
    long countTotalOrders();

    /**
     * Count orders by status.
     * 
     * @param status the order status
     * @return count of orders with the specified status
     */
    long countByOrderStatus(Order.OrderStatus status);

    /**
     * Find orders by customer email.
     * 
     * @param email the customer email
     * @return list of orders for the customer
     */
    @Query("SELECT o FROM Order o WHERE o.customer.email = :email ORDER BY o.orderDate DESC")
    List<Order> findByCustomerEmail(@Param("email") String email);

    /**
     * Find all orders with customer and order items details.
     * 
     * @return list of all orders with complete details
     */
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.customer LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product ORDER BY o.orderDate DESC")
    List<Order> findAllWithDetails();

    /**
     * Find orders by customer name (case-insensitive).
     * 
     * @param customerName the customer name
     * @return list of orders for customers matching the name
     */
    @Query("SELECT o FROM Order o WHERE LOWER(o.customer.customerName) LIKE LOWER(CONCAT('%', :customerName, '%')) ORDER BY o.orderDate DESC")
    List<Order> findByCustomerNameContainingIgnoreCase(@Param("customerName") String customerName);

    /**
     * Find top customers by order count.
     * 
     * @param limit the maximum number of customers to return
     * @return list of customer IDs with their order counts
     */
    @Query("SELECT o.customer.customerId, COUNT(o) as orderCount FROM Order o GROUP BY o.customer.customerId ORDER BY orderCount DESC")
    List<Object[]> findTopCustomersByOrderCount();
}
