package com.grocery.ordering.security;

import com.grocery.ordering.entity.AdminUser;
import com.grocery.ordering.entity.Customer;
import com.grocery.ordering.repository.AdminUserRepository;
import com.grocery.ordering.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserDetailsService implementation for loading user details.
 * Supports both customer and admin user authentication.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First try to find admin user by username
        Optional<AdminUser> adminUser = adminUserRepository.findByUsernameAndIsActive(username, true);
        if (adminUser.isPresent()) {
            return UserPrincipal.create(adminUser.get());
        }

        // Then try to find admin user by email
        adminUser = adminUserRepository.findByEmailAndIsActive(username, true);
        if (adminUser.isPresent()) {
            return UserPrincipal.create(adminUser.get());
        }

        // Finally try to find customer by email
        Optional<Customer> customer = customerRepository.findByEmailAndIsActive(username, true);
        if (customer.isPresent()) {
            return UserPrincipal.create(customer.get());
        }

        throw new UsernameNotFoundException("User not found with username or email: " + username);
    }

    /**
     * Load user details by customer ID.
     * 
     * @param customerId the customer ID
     * @return UserDetails for the customer
     * @throws UsernameNotFoundException if customer not found
     */
    @Transactional
    public UserDetails loadUserByCustomerId(Long customerId) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent() && customer.get().getIsActive()) {
            return UserPrincipal.create(customer.get());
        }

        throw new UsernameNotFoundException("Customer not found with id: " + customerId);
    }

    /**
     * Load user details by admin user ID.
     * 
     * @param adminId the admin user ID
     * @return UserDetails for the admin user
     * @throws UsernameNotFoundException if admin user not found
     */
    @Transactional
    public UserDetails loadUserByAdminId(Long adminId) throws UsernameNotFoundException {
        Optional<AdminUser> adminUser = adminUserRepository.findById(adminId);
        if (adminUser.isPresent() && adminUser.get().getIsActive()) {
            return UserPrincipal.create(adminUser.get());
        }

        throw new UsernameNotFoundException("Admin user not found with id: " + adminId);
    }
}
