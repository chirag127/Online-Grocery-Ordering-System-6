package com.grocery.ordering.repository;

import com.grocery.ordering.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AdminUser entity operations.
 * Provides CRUD operations and custom queries for admin user management.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    /**
     * Find admin user by username.
     * 
     * @param username the username
     * @return Optional containing the admin user if found
     */
    Optional<AdminUser> findByUsername(String username);

    /**
     * Find admin user by username and active status.
     * 
     * @param username the username
     * @param isActive the active status
     * @return Optional containing the admin user if found
     */
    Optional<AdminUser> findByUsernameAndIsActive(String username, Boolean isActive);

    /**
     * Find admin user by email.
     * 
     * @param email the email address
     * @return Optional containing the admin user if found
     */
    Optional<AdminUser> findByEmail(String email);

    /**
     * Find admin user by email and active status.
     * 
     * @param email the email address
     * @param isActive the active status
     * @return Optional containing the admin user if found
     */
    Optional<AdminUser> findByEmailAndIsActive(String email, Boolean isActive);

    /**
     * Check if admin user exists by username.
     * 
     * @param username the username
     * @return true if admin user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if admin user exists by email.
     * 
     * @param email the email address
     * @return true if admin user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find all active admin users.
     * 
     * @return list of active admin users
     */
    List<AdminUser> findByIsActiveTrue();

    /**
     * Find admin users by role.
     * 
     * @param role the admin role
     * @return list of admin users with the specified role
     */
    List<AdminUser> findByRoleAndIsActiveTrue(AdminUser.Role role);

    /**
     * Find admin users by full name (case-insensitive search).
     * 
     * @param fullName the full name to search for
     * @return list of admin users matching the name
     */
    @Query("SELECT a FROM AdminUser a WHERE LOWER(a.fullName) LIKE LOWER(CONCAT('%', :fullName, '%')) AND a.isActive = true")
    List<AdminUser> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);

    /**
     * Count total number of active admin users.
     * 
     * @return count of active admin users
     */
    @Query("SELECT COUNT(a) FROM AdminUser a WHERE a.isActive = true")
    long countActiveAdminUsers();

    /**
     * Count admin users by role.
     * 
     * @param role the admin role
     * @return count of admin users with the specified role
     */
    long countByRoleAndIsActiveTrue(AdminUser.Role role);
}
