package com.grocery.ordering.service;

import com.grocery.ordering.dto.OrderDTO;
import com.grocery.ordering.dto.OrderItemDTO;
import com.grocery.ordering.entity.Customer;
import com.grocery.ordering.entity.Order;
import com.grocery.ordering.entity.OrderItem;
import com.grocery.ordering.entity.Product;
import com.grocery.ordering.repository.CustomerRepository;
import com.grocery.ordering.repository.OrderRepository;
import com.grocery.ordering.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Order operations.
 * Handles business logic for order management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create a new order.
     * 
     * @param orderDTO the order data
     * @return OrderDTO of the created order
     * @throws RuntimeException if order creation fails
     */
    public OrderDTO createOrder(OrderDTO orderDTO) {
        logger.info("Creating new order for customer ID: {}", orderDTO.getCustomerId());

        // Validate customer exists
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + orderDTO.getCustomerId()));

        // Create new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setDeliveryAddress(orderDTO.getDeliveryAddress());
        order.setContactNumber(orderDTO.getContactNumber());
        order.setOrderStatus(Order.OrderStatus.PENDING);

        // Save order first to get ID
        Order savedOrder = orderRepository.save(order);

        // Process order items if provided
        if (orderDTO.getOrderItems() != null && !orderDTO.getOrderItems().isEmpty()) {
            BigDecimal calculatedTotal = BigDecimal.ZERO;
            
            for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
                Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + itemDTO.getProductId()));

                // Check if sufficient quantity is available
                if (product.getQuantity() < itemDTO.getQuantity()) {
                    throw new RuntimeException("Insufficient quantity for product: " + product.getProductName() + 
                        ". Available: " + product.getQuantity() + ", Requested: " + itemDTO.getQuantity());
                }

                // Create order item
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(product);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setUnitPrice(product.getPrice());
                orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

                // Update product quantity
                product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
                productRepository.save(product);

                calculatedTotal = calculatedTotal.add(orderItem.getTotalPrice());
            }

            // Update order total amount
            savedOrder.setTotalAmount(calculatedTotal);
            savedOrder = orderRepository.save(savedOrder);
        }

        logger.info("Order created successfully with ID: {}", savedOrder.getOrderId());
        return convertToDTO(savedOrder);
    }

    /**
     * Get customer order details.
     * 
     * @param customerId the customer ID
     * @return list of OrderDTOs for the customer
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getCustomerOrderDetails(Long customerId) {
        logger.info("Fetching order details for customer ID: {}", customerId);

        // Validate customer exists
        customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        List<Order> orders = orderRepository.findByCustomerCustomerIdWithItems(customerId);
        
        return orders.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get order by ID.
     * 
     * @param orderId the order ID
     * @return OrderDTO
     * @throws RuntimeException if order not found
     */
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        return convertToDTO(order);
    }

    /**
     * Update order status.
     * 
     * @param orderId the order ID
     * @param status the new order status
     * @return updated OrderDTO
     * @throws RuntimeException if order not found
     */
    public OrderDTO updateOrderStatus(Long orderId, Order.OrderStatus status) {
        logger.info("Updating order status for ID: {} to {}", orderId, status);

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setOrderStatus(status);
        Order updatedOrder = orderRepository.save(order);

        logger.info("Order status updated successfully for ID: {}", orderId);
        return convertToDTO(updatedOrder);
    }

    /**
     * Get all orders.
     * 
     * @return list of all OrderDTOs
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllWithDetails();
        return orders.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get orders by status.
     * 
     * @param status the order status
     * @return list of OrderDTOs with the specified status
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(Order.OrderStatus status) {
        List<Order> orders = orderRepository.findByOrderStatus(status);
        return orders.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get recent orders (last 30 days).
     * 
     * @return list of recent OrderDTOs
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getRecentOrders() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Order> orders = orderRepository.findRecentOrders(thirtyDaysAgo);
        return orders.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Cancel order.
     * 
     * @param orderId the order ID
     * @return updated OrderDTO
     * @throws RuntimeException if order not found or cannot be cancelled
     */
    public OrderDTO cancelOrder(Long orderId) {
        logger.info("Cancelling order with ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Check if order can be cancelled
        if (order.getOrderStatus() == Order.OrderStatus.DELIVERED || 
            order.getOrderStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Order cannot be cancelled. Current status: " + order.getOrderStatus());
        }

        // Restore product quantities if order is cancelled
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }

        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        Order updatedOrder = orderRepository.save(order);

        logger.info("Order cancelled successfully with ID: {}", orderId);
        return convertToDTO(updatedOrder);
    }

    /**
     * Convert Order entity to OrderDTO.
     * 
     * @param order the order entity
     * @return OrderDTO
     */
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        dto.setCustomerName(order.getCustomer().getCustomerName());
        dto.setCustomerEmail(order.getCustomer().getEmail());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setContactNumber(order.getContactNumber());

        // Convert order items
        if (order.getOrderItems() != null) {
            List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
                .map(this::convertOrderItemToDTO)
                .collect(Collectors.toList());
            dto.setOrderItems(orderItemDTOs);
        }

        return dto;
    }

    /**
     * Convert OrderItem entity to OrderItemDTO.
     * 
     * @param orderItem the order item entity
     * @return OrderItemDTO
     */
    private OrderItemDTO convertOrderItemToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setOrderId(orderItem.getOrder().getOrderId());
        dto.setProductId(orderItem.getProduct().getProductId());
        dto.setProductName(orderItem.getProduct().getProductName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setTotalPrice(orderItem.getTotalPrice());
        return dto;
    }
}
