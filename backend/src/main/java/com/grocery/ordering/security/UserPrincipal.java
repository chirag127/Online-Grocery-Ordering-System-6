package com.grocery.ordering.security;

import com.grocery.ordering.entity.AdminUser;
import com.grocery.ordering.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * UserPrincipal class implementing UserDetails for Spring Security.
 * Represents the authenticated user in the security context.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean isActive;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor for Customer
    public UserPrincipal(Long id, String username, String email, String password, boolean isActive, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.authorities = authorities;
        this.role = "CUSTOMER";
    }

    // Constructor for AdminUser
    public UserPrincipal(Long id, String username, String email, String password, String role, boolean isActive, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.authorities = authorities;
    }

    /**
     * Create UserPrincipal from Customer entity.
     * 
     * @param customer the customer entity
     * @return UserPrincipal instance
     */
    public static UserPrincipal create(Customer customer) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_CUSTOMER")
        );

        return new UserPrincipal(
            customer.getCustomerId(),
            customer.getEmail(),
            customer.getEmail(),
            customer.getPassword(),
            customer.getIsActive(),
            authorities
        );
    }

    /**
     * Create UserPrincipal from AdminUser entity.
     * 
     * @param adminUser the admin user entity
     * @return UserPrincipal instance
     */
    public static UserPrincipal create(AdminUser adminUser) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + adminUser.getRole().name())
        );

        return new UserPrincipal(
            adminUser.getId(),
            adminUser.getUsername(),
            adminUser.getEmail(),
            adminUser.getPassword(),
            adminUser.getRole().name(),
            adminUser.getIsActive(),
            authorities
        );
    }

    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
