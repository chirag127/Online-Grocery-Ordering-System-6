package com.grocery.ordering.service;

import com.grocery.ordering.dto.CustomerDTO;
import com.grocery.ordering.dto.CustomerRegistrationDTO;
import com.grocery.ordering.entity.Customer;
import com.grocery.ordering.repository.CustomerRepository;
import com.grocery.ordering.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Customer operations.
 * Handles business logic for customer management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Service
@Transactional
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new customer.
     * 
     * @param registrationDTO the customer registration data
     * @return CustomerDTO of the registered customer
     * @throws RuntimeException if registration fails
     */
    public CustomerDTO registerCustomer(CustomerRegistrationDTO registrationDTO) {
        logger.info("Registering new customer with email: {}", registrationDTO.getEmail());

        // Validate input
        ValidationUtils.validateCustomerRegistration(registrationDTO);

        // Check if email already exists
        if (customerRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + registrationDTO.getEmail());
        }

        // Check if passwords match
        if (!registrationDTO.isPasswordMatching()) {
            throw new RuntimeException("Passwords do not match");
        }

        // Create new customer
        Customer customer = new Customer();
        customer.setCustomerName(registrationDTO.getCustomerName());
        customer.setEmail(registrationDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        customer.setAddress(registrationDTO.getAddress());
        customer.setContactNumber(registrationDTO.getContactNumber());

        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer registered successfully with ID: {}", savedCustomer.getCustomerId());

        return convertToDTO(savedCustomer);
    }

    /**
     * Update customer details.
     * 
     * @param customerId the customer ID
     * @param customerDTO the updated customer data
     * @return updated CustomerDTO
     * @throws RuntimeException if customer not found or update fails
     */
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        logger.info("Updating customer with ID: {}", customerId);

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Validate input
        ValidationUtils.validateCustomerUpdate(customerDTO);

        // Check if email is being changed and if it already exists
        if (!customer.getEmail().equals(customerDTO.getEmail()) && 
            customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + customerDTO.getEmail());
        }

        // Update customer details
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setContactNumber(customerDTO.getContactNumber());

        Customer updatedCustomer = customerRepository.save(customer);
        logger.info("Customer updated successfully with ID: {}", updatedCustomer.getCustomerId());

        return convertToDTO(updatedCustomer);
    }

    /**
     * Update customer password.
     * 
     * @param customerId the customer ID
     * @param newPassword the new password
     * @throws RuntimeException if customer not found or password invalid
     */
    public void updateCustomerPassword(Long customerId, String newPassword) {
        logger.info("Updating password for customer ID: {}", customerId);

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Validate password
        ValidationUtils.validatePassword(newPassword);

        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);

        logger.info("Password updated successfully for customer ID: {}", customerId);
    }

    /**
     * Get customer by ID.
     * 
     * @param customerId the customer ID
     * @return CustomerDTO
     * @throws RuntimeException if customer not found
     */
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        return convertToDTO(customer);
    }

    /**
     * Get customer by email.
     * 
     * @param email the customer email
     * @return CustomerDTO
     * @throws RuntimeException if customer not found
     */
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmailAndIsActive(email, true)
            .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        return convertToDTO(customer);
    }

    /**
     * Search customers by name.
     * 
     * @param customerName the customer name to search for
     * @return list of CustomerDTOs matching the search criteria
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomersByName(String customerName) {
        logger.info("Searching customers by name: {}", customerName);

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new RuntimeException("Customer name cannot be empty");
        }

        List<Customer> customers = customerRepository.findByCustomerNameContainingIgnoreCase(customerName.trim());
        
        if (customers.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        return customers.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get all active customers.
     * 
     * @return list of all active CustomerDTOs
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllActiveCustomers() {
        List<Customer> customers = customerRepository.findByIsActiveTrue();
        return customers.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Deactivate customer account.
     * 
     * @param customerId the customer ID
     */
    public void deactivateCustomer(Long customerId) {
        logger.info("Deactivating customer with ID: {}", customerId);

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        customer.setIsActive(false);
        customerRepository.save(customer);

        logger.info("Customer deactivated successfully with ID: {}", customerId);
    }

    /**
     * Convert Customer entity to CustomerDTO.
     * 
     * @param customer the customer entity
     * @return CustomerDTO
     */
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setContactNumber(customer.getContactNumber());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        dto.setIsActive(customer.getIsActive());
        return dto;
    }
}
