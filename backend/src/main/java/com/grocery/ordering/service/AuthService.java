package com.grocery.ordering.service;

import com.grocery.ordering.dto.AuthResponseDTO;
import com.grocery.ordering.dto.LoginDTO;
import com.grocery.ordering.entity.AdminUser;
import com.grocery.ordering.entity.Customer;
import com.grocery.ordering.repository.AdminUserRepository;
import com.grocery.ordering.repository.CustomerRepository;
import com.grocery.ordering.security.JwtUtils;
import com.grocery.ordering.security.UserPrincipal;
import com.grocery.ordering.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for Authentication operations.
 * Handles login, logout, and authentication-related business logic.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Service
@Transactional
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Authenticate user and generate JWT token.
     * 
     * @param loginDTO the login credentials
     * @return AuthResponseDTO with token and user details
     * @throws RuntimeException if authentication fails
     */
    public AuthResponseDTO authenticateUser(LoginDTO loginDTO) {
        logger.info("Authenticating user: {}", loginDTO.getUsername());

        try {
            // Validate input
            ValidationUtils.preventSQLInjection(loginDTO.getUsername(), "Username");
            ValidationUtils.preventSQLInjection(loginDTO.getPassword(), "Password");

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            logger.info("User authenticated successfully: {}", userPrincipal.getUsername());

            return new AuthResponseDTO(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                userPrincipal.getRole()
            );

        } catch (AuthenticationException e) {
            logger.error("Authentication failed for user: {}", loginDTO.getUsername());
            throw new RuntimeException("Please Enter Correct UserName and Password");
        }
    }

    /**
     * Authenticate admin user specifically.
     * 
     * @param loginDTO the login credentials
     * @return AuthResponseDTO with token and admin details
     * @throws RuntimeException if authentication fails
     */
    public AuthResponseDTO authenticateAdmin(LoginDTO loginDTO) {
        logger.info("Authenticating admin user: {}", loginDTO.getUsername());

        try {
            // Validate input
            ValidationUtils.preventSQLInjection(loginDTO.getUsername(), "Username");
            ValidationUtils.preventSQLInjection(loginDTO.getPassword(), "Password");

            // Check if user is admin
            Optional<AdminUser> adminUser = adminUserRepository.findByUsernameAndIsActive(loginDTO.getUsername(), true);
            if (adminUser.isEmpty()) {
                adminUser = adminUserRepository.findByEmailAndIsActive(loginDTO.getUsername(), true);
            }

            if (adminUser.isEmpty()) {
                throw new RuntimeException("Please Enter Correct UserName and Password");
            }

            // Authenticate admin
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            logger.info("Admin authenticated successfully: {}", userPrincipal.getUsername());

            return new AuthResponseDTO(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                userPrincipal.getRole()
            );

        } catch (AuthenticationException e) {
            logger.error("Admin authentication failed for user: {}", loginDTO.getUsername());
            throw new RuntimeException("Please Enter Correct UserName and Password");
        }
    }

    /**
     * Authenticate customer user specifically.
     * 
     * @param loginDTO the login credentials
     * @return AuthResponseDTO with token and customer details
     * @throws RuntimeException if authentication fails
     */
    public AuthResponseDTO authenticateCustomer(LoginDTO loginDTO) {
        logger.info("Authenticating customer user: {}", loginDTO.getUsername());

        try {
            // Validate input
            ValidationUtils.preventSQLInjection(loginDTO.getUsername(), "Username");
            ValidationUtils.preventSQLInjection(loginDTO.getPassword(), "Password");

            // Check if user is customer
            Optional<Customer> customer = customerRepository.findByEmailAndIsActive(loginDTO.getUsername(), true);
            if (customer.isEmpty()) {
                throw new RuntimeException("Please Enter Correct UserName and Password");
            }

            // Authenticate customer
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            logger.info("Customer authenticated successfully: {}", userPrincipal.getUsername());

            return new AuthResponseDTO(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                userPrincipal.getRole()
            );

        } catch (AuthenticationException e) {
            logger.error("Customer authentication failed for user: {}", loginDTO.getUsername());
            throw new RuntimeException("Please Enter Correct UserName and Password");
        }
    }

    /**
     * Logout user by clearing security context.
     * Note: JWT tokens are stateless, so actual logout is handled on client side.
     * 
     * @return success message
     */
    public AuthResponseDTO logoutUser() {
        SecurityContextHolder.clearContext();
        logger.info("User logged out successfully");
        return new AuthResponseDTO("Logout successful", true);
    }

    /**
     * Validate JWT token.
     * 
     * @param token the JWT token to validate
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        return jwtUtils.validateJwtToken(token);
    }

    /**
     * Get username from JWT token.
     * 
     * @param token the JWT token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return jwtUtils.getUserNameFromJwtToken(token);
    }

    /**
     * Check if user exists by username/email.
     * 
     * @param username the username or email
     * @return true if user exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean userExists(String username) {
        // Check admin users
        if (adminUserRepository.findByUsernameAndIsActive(username, true).isPresent() ||
            adminUserRepository.findByEmailAndIsActive(username, true).isPresent()) {
            return true;
        }

        // Check customers
        return customerRepository.findByEmailAndIsActive(username, true).isPresent();
    }

    /**
     * Get current authenticated user details.
     * 
     * @return UserPrincipal of current user
     * @throws RuntimeException if no user is authenticated
     */
    public UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        return (UserPrincipal) authentication.getPrincipal();
    }
}
