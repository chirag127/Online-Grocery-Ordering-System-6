package com.grocery.ordering.controller;

import com.grocery.ordering.dto.AuthResponseDTO;
import com.grocery.ordering.dto.CustomerRegistrationDTO;
import com.grocery.ordering.dto.LoginDTO;
import com.grocery.ordering.service.AuthService;
import com.grocery.ordering.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for authentication operations.
 * Handles login, logout, and registration endpoints.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    /**
     * Authenticate user (admin or customer).
     * 
     * @param loginDTO the login credentials
     * @return ResponseEntity with authentication result
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            logger.info("Login attempt for user: {}", loginDTO.getUsername());
            
            AuthResponseDTO response = authService.authenticateUser(loginDTO);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Login failed for user: {}", loginDTO.getUsername(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Authenticate admin user specifically.
     * 
     * @param loginDTO the login credentials
     * @return ResponseEntity with authentication result
     */
    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            logger.info("Admin login attempt for user: {}", loginDTO.getUsername());
            
            AuthResponseDTO response = authService.authenticateAdmin(loginDTO);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Admin login failed for user: {}", loginDTO.getUsername(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Authenticate customer user specifically.
     * 
     * @param loginDTO the login credentials
     * @return ResponseEntity with authentication result
     */
    @PostMapping("/customer/login")
    public ResponseEntity<?> authenticateCustomer(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            logger.info("Customer login attempt for user: {}", loginDTO.getUsername());
            
            AuthResponseDTO response = authService.authenticateCustomer(loginDTO);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Customer login failed for user: {}", loginDTO.getUsername(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    /**
     * Register new customer.
     * 
     * @param registrationDTO the customer registration data
     * @return ResponseEntity with registration result
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationDTO registrationDTO) {
        try {
            logger.info("Customer registration attempt for email: {}", registrationDTO.getEmail());
            
            customerService.registerCustomer(registrationDTO);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer registered successfully");
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            logger.error("Customer registration failed for email: {}", registrationDTO.getEmail(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Logout user.
     * 
     * @return ResponseEntity with logout result
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        try {
            AuthResponseDTO response = authService.logoutUser();
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Logout failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Logout failed");
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Validate JWT token.
     * 
     * @param token the JWT token to validate
     * @return ResponseEntity with validation result
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        try {
            boolean isValid = authService.validateToken(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("valid", isValid);
            response.put("timestamp", System.currentTimeMillis());
            
            if (isValid) {
                String username = authService.getUsernameFromToken(token);
                response.put("username", username);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Token validation failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("valid", false);
            errorResponse.put("message", "Token validation failed");
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Check if user exists.
     * 
     * @param username the username or email to check
     * @return ResponseEntity with existence result
     */
    @GetMapping("/exists")
    public ResponseEntity<?> checkUserExists(@RequestParam String username) {
        try {
            boolean exists = authService.userExists(username);
            
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            response.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("User existence check failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("exists", false);
            errorResponse.put("message", "User existence check failed");
            errorResponse.put("timestamp", System.currentTimeMillis());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
