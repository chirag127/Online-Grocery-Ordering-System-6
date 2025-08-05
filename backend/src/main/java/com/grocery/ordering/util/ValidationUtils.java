package com.grocery.ordering.util;

import com.grocery.ordering.dto.CustomerDTO;
import com.grocery.ordering.dto.CustomerRegistrationDTO;
import com.grocery.ordering.dto.ProductDTO;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Utility class for input validation and SQL injection prevention.
 * Provides comprehensive validation methods for all user inputs.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
public class ValidationUtils {

    // Regular expressions for validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );
    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{2,100}$");
    
    // SQL injection patterns to detect and prevent
    private static final Pattern[] SQL_INJECTION_PATTERNS = {
        Pattern.compile("(?i).*('|(\\-\\-)|(;)|(\\|)|(\\*)).*"),
        Pattern.compile("(?i).*(union|select|insert|update|delete|drop|create|alter|exec|execute).*"),
        Pattern.compile("(?i).*(script|javascript|vbscript|onload|onerror|onclick).*"),
        Pattern.compile("(?i).*(\\<|\\>|\\&lt|\\&gt).*")
    };

    /**
     * Validate customer registration data.
     * 
     * @param registrationDTO the registration data to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateCustomerRegistration(CustomerRegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            throw new RuntimeException("Registration data cannot be null");
        }

        validateCustomerName(registrationDTO.getCustomerName());
        validateEmail(registrationDTO.getEmail());
        validatePassword(registrationDTO.getPassword());
        validateAddress(registrationDTO.getAddress());
        validateContactNumber(registrationDTO.getContactNumber());
        
        // Check for SQL injection in all fields
        preventSQLInjection(registrationDTO.getCustomerName(), "Customer Name");
        preventSQLInjection(registrationDTO.getEmail(), "Email");
        preventSQLInjection(registrationDTO.getAddress(), "Address");
        preventSQLInjection(registrationDTO.getContactNumber(), "Contact Number");
    }

    /**
     * Validate customer update data.
     * 
     * @param customerDTO the customer data to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateCustomerUpdate(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            throw new RuntimeException("Customer data cannot be null");
        }

        validateCustomerName(customerDTO.getCustomerName());
        validateEmail(customerDTO.getEmail());
        validateAddress(customerDTO.getAddress());
        validateContactNumber(customerDTO.getContactNumber());
        
        // Check for SQL injection in all fields
        preventSQLInjection(customerDTO.getCustomerName(), "Customer Name");
        preventSQLInjection(customerDTO.getEmail(), "Email");
        preventSQLInjection(customerDTO.getAddress(), "Address");
        preventSQLInjection(customerDTO.getContactNumber(), "Contact Number");
    }

    /**
     * Validate product data.
     * 
     * @param productDTO the product data to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new RuntimeException("Product data cannot be null");
        }

        validateProductName(productDTO.getProductName());
        validatePrice(productDTO.getPrice());
        validateQuantity(productDTO.getQuantity());
        
        if (StringUtils.hasText(productDTO.getCategory())) {
            validateCategory(productDTO.getCategory());
        }
        
        if (StringUtils.hasText(productDTO.getDescription())) {
            validateDescription(productDTO.getDescription());
        }
        
        // Check for SQL injection in all fields
        preventSQLInjection(productDTO.getProductName(), "Product Name");
        if (productDTO.getCategory() != null) {
            preventSQLInjection(productDTO.getCategory(), "Category");
        }
        if (productDTO.getDescription() != null) {
            preventSQLInjection(productDTO.getDescription(), "Description");
        }
    }

    /**
     * Validate customer name.
     * 
     * @param customerName the customer name to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateCustomerName(String customerName) {
        if (!StringUtils.hasText(customerName)) {
            throw new RuntimeException("Customer name is required");
        }
        
        if (customerName.length() > 100) {
            throw new RuntimeException("Customer name must not exceed 100 characters");
        }
        
        if (!NAME_PATTERN.matcher(customerName.trim()).matches()) {
            throw new RuntimeException("Customer name must contain only letters and spaces");
        }
    }

    /**
     * Validate email address.
     * 
     * @param email the email to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new RuntimeException("Email is required");
        }
        
        if (email.length() > 100) {
            throw new RuntimeException("Email must not exceed 100 characters");
        }
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new RuntimeException("Email format is invalid");
        }
    }

    /**
     * Validate password.
     * 
     * @param password the password to validate
     * @throws RuntimeException if validation fails
     */
    public static void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new RuntimeException("Password is required");
        }
        
        if (password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }
        
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new RuntimeException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }
    }

    /**
     * Validate contact number.
     * 
     * @param contactNumber the contact number to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateContactNumber(String contactNumber) {
        if (!StringUtils.hasText(contactNumber)) {
            throw new RuntimeException("Contact number is required");
        }
        
        if (!PHONE_PATTERN.matcher(contactNumber.trim()).matches()) {
            throw new RuntimeException("Contact number must be exactly 10 digits");
        }
    }

    /**
     * Validate address.
     * 
     * @param address the address to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateAddress(String address) {
        if (!StringUtils.hasText(address)) {
            throw new RuntimeException("Address is required");
        }
        
        if (address.length() > 500) {
            throw new RuntimeException("Address must not exceed 500 characters");
        }
    }

    /**
     * Validate product name.
     * 
     * @param productName the product name to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateProductName(String productName) {
        if (!StringUtils.hasText(productName)) {
            throw new RuntimeException("Product name is required");
        }
        
        if (productName.length() > 100) {
            throw new RuntimeException("Product name must not exceed 100 characters");
        }
    }

    /**
     * Validate price.
     * 
     * @param price the price to validate
     * @throws RuntimeException if validation fails
     */
    public static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new RuntimeException("Price is required");
        }
        
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }
        
        if (price.scale() > 2) {
            throw new RuntimeException("Price can have at most 2 decimal places");
        }
    }

    /**
     * Validate quantity.
     * 
     * @param quantity the quantity to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateQuantity(Integer quantity) {
        if (quantity == null) {
            throw new RuntimeException("Quantity is required");
        }
        
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
    }

    /**
     * Validate category.
     * 
     * @param category the category to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateCategory(String category) {
        if (category.length() > 50) {
            throw new RuntimeException("Category must not exceed 50 characters");
        }
    }

    /**
     * Validate description.
     * 
     * @param description the description to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateDescription(String description) {
        if (description.length() > 1000) {
            throw new RuntimeException("Description must not exceed 1000 characters");
        }
    }

    /**
     * Prevent SQL injection attacks by checking input against known patterns.
     * 
     * @param input the input string to check
     * @param fieldName the name of the field being validated
     * @throws RuntimeException if SQL injection is detected
     */
    public static void preventSQLInjection(String input, String fieldName) {
        if (!StringUtils.hasText(input)) {
            return;
        }

        String cleanInput = input.trim();
        
        for (Pattern pattern : SQL_INJECTION_PATTERNS) {
            if (pattern.matcher(cleanInput).matches()) {
                throw new RuntimeException("Invalid characters detected in " + fieldName + ". Please use only alphanumeric characters and spaces.");
            }
        }
    }

    /**
     * Sanitize input string by removing potentially dangerous characters.
     * 
     * @param input the input string to sanitize
     * @return sanitized string
     */
    public static String sanitizeInput(String input) {
        if (!StringUtils.hasText(input)) {
            return input;
        }
        
        return input.trim()
            .replaceAll("[<>\"'%;()&+]", "")
            .replaceAll("--", "")
            .replaceAll("/\\*", "")
            .replaceAll("\\*/", "");
    }

    /**
     * Validate search term for safe database queries.
     * 
     * @param searchTerm the search term to validate
     * @throws RuntimeException if validation fails
     */
    public static void validateSearchTerm(String searchTerm) {
        if (!StringUtils.hasText(searchTerm)) {
            throw new RuntimeException("Search term cannot be empty");
        }
        
        if (searchTerm.length() > 100) {
            throw new RuntimeException("Search term must not exceed 100 characters");
        }
        
        preventSQLInjection(searchTerm, "Search Term");
    }
}
