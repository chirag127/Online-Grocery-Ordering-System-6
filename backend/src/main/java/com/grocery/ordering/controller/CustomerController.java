package com.grocery.ordering.controller;

import com.grocery.ordering.dto.CustomerDTO;
import com.grocery.ordering.dto.OrderDTO;
import com.grocery.ordering.security.UserPrincipal;
import com.grocery.ordering.service.CustomerService;
import com.grocery.ordering.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for customer operations.
 * Handles customer-related endpoints.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    /**
     * Get current customer profile.
     * 
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with customer details
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCurrentCustomer(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            CustomerDTO customer = customerService.getCustomerById(userPrincipal.getId());
            return ResponseEntity.ok(customer);
            
        } catch (Exception e) {
            logger.error("Failed to get customer profile for ID: {}", userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Update customer profile.
     * 
     * @param customerDTO the updated customer data
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with update result
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(userPrincipal.getId(), customerDTO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer profile updated successfully");
            response.put("customer", updatedCustomer);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to update customer profile for ID: {}", userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Update customer password.
     * 
     * @param passwordData the password update data
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with update result
     */
    @PutMapping("/password")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwordData,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            String newPassword = passwordData.get("newPassword");
            if (newPassword == null || newPassword.trim().isEmpty()) {
                throw new RuntimeException("New password is required");
            }
            
            customerService.updateCustomerPassword(userPrincipal.getId(), newPassword);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Password updated successfully");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to update password for customer ID: {}", userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get customer order history.
     * 
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with order history
     */
    @GetMapping("/orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCustomerOrders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            List<OrderDTO> orders = orderService.getCustomerOrderDetails(userPrincipal.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("orders", orders);
            response.put("count", orders.size());
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to get orders for customer ID: {}", userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Create new order.
     * 
     * @param orderDTO the order data
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with order creation result
     */
    @PostMapping("/orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            // Set customer ID from authenticated user
            orderDTO.setCustomerId(userPrincipal.getId());
            
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order created successfully");
            response.put("order", createdOrder);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            logger.error("Failed to create order for customer ID: {}", userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get specific order details.
     * 
     * @param orderId the order ID
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with order details
     */
    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            
            // Verify that the order belongs to the authenticated customer
            if (!order.getCustomerId().equals(userPrincipal.getId())) {
                throw new RuntimeException("Access denied: Order does not belong to the authenticated customer");
            }
            
            return ResponseEntity.ok(order);
            
        } catch (Exception e) {
            logger.error("Failed to get order details for order ID: {} and customer ID: {}", orderId, userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Cancel order.
     * 
     * @param orderId the order ID
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with cancellation result
     */
    @PutMapping("/orders/{orderId}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            // First verify that the order belongs to the authenticated customer
            OrderDTO order = orderService.getOrderById(orderId);
            if (!order.getCustomerId().equals(userPrincipal.getId())) {
                throw new RuntimeException("Access denied: Order does not belong to the authenticated customer");
            }
            
            OrderDTO cancelledOrder = orderService.cancelOrder(orderId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order cancelled successfully");
            response.put("order", cancelledOrder);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to cancel order ID: {} for customer ID: {}", orderId, userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Deactivate customer account.
     * 
     * @param userPrincipal the authenticated user
     * @return ResponseEntity with deactivation result
     */
    @DeleteMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deactivateAccount(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            customerService.deactivateCustomer(userPrincipal.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Account deactivated successfully");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Failed to deactivate account for customer ID: {}", userPrincipal.getId(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
