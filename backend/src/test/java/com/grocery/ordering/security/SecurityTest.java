package com.grocery.ordering.security;

import com.grocery.ordering.util.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Security tests for the grocery ordering system.
 * Tests SQL injection prevention and input validation.
 *
 * @author Chirag Singhal
 * @version 1.0.0
 */
public class SecurityTest {

    @Test
    @DisplayName("Test SQL Injection Prevention - Basic Injection")
    public void testSQLInjectionPrevention() {
        // Test basic SQL injection attempts
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("'; DROP TABLE users; --", "Username");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("admin' OR '1'='1", "Username");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("1' UNION SELECT * FROM customers", "Search Term");
        });
    }

    @Test
    @DisplayName("Test SQL Injection Prevention - Advanced Injection")
    public void testAdvancedSQLInjectionPrevention() {
        // Test advanced SQL injection attempts
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("admin'; INSERT INTO admin_users VALUES('hacker', 'pass'); --", "Username");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("1' OR 1=1 /*", "Product ID");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("'; EXEC xp_cmdshell('dir'); --", "Input");
        });
    }

    @Test
    @DisplayName("Test XSS Prevention")
    public void testXSSPrevention() {
        // Test XSS script injection attempts
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("<script>alert('XSS')</script>", "Customer Name");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("javascript:alert('XSS')", "Product Name");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.preventSQLInjection("<img src=x onerror=alert('XSS')>", "Description");
        });
    }

    @Test
    @DisplayName("Test Valid Input Acceptance")
    public void testValidInputAcceptance() {
        // Test that valid inputs are accepted
        assertDoesNotThrow(() -> {
            ValidationUtils.preventSQLInjection("John Doe", "Customer Name");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.preventSQLInjection("Fresh Apples", "Product Name");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.preventSQLInjection("john.doe@email.com", "Email");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.preventSQLInjection("123 Main Street", "Address");
        });
    }

    @Test
    @DisplayName("Test Password Validation")
    public void testPasswordValidation() {
        // Test weak passwords are rejected
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validatePassword("123");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validatePassword("password");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validatePassword("PASSWORD");
        });

        // Test strong passwords are accepted
        assertDoesNotThrow(() -> {
            ValidationUtils.validatePassword("StrongPass123!");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.validatePassword("MySecure@Pass1");
        });
    }

    @Test
    @DisplayName("Test Email Validation")
    public void testEmailValidation() {
        // Test invalid emails are rejected
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateEmail("invalid-email");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateEmail("user@");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateEmail("@domain.com");
        });

        // Test valid emails are accepted
        assertDoesNotThrow(() -> {
            ValidationUtils.validateEmail("user@domain.com");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.validateEmail("john.doe@example.org");
        });
    }

    @Test
    @DisplayName("Test Contact Number Validation")
    public void testContactNumberValidation() {
        // Test invalid contact numbers are rejected
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateContactNumber("123");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateContactNumber("12345678901");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateContactNumber("abcdefghij");
        });

        // Test valid contact numbers are accepted
        assertDoesNotThrow(() -> {
            ValidationUtils.validateContactNumber("9876543210");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.validateContactNumber("1234567890");
        });
    }

    @Test
    @DisplayName("Test Input Sanitization")
    public void testInputSanitization() {
        // Test that dangerous characters are removed
        String sanitized = ValidationUtils.sanitizeInput("Hello<script>alert('xss')</script>World");
        assertFalse(sanitized.contains("<script>"));
        assertFalse(sanitized.contains("</script>"));

        String sanitized2 = ValidationUtils.sanitizeInput("SELECT * FROM users; --");
        assertFalse(sanitized2.contains("--"));

        String sanitized3 = ValidationUtils.sanitizeInput("Normal text with spaces");
        assertEquals("Normal text with spaces", sanitized3);
    }

    @Test
    @DisplayName("Test Search Term Validation")
    public void testSearchTermValidation() {
        // Test malicious search terms are rejected
        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateSearchTerm("'; DROP TABLE products; --");
        });

        assertThrows(RuntimeException.class, () -> {
            ValidationUtils.validateSearchTerm("1' OR '1'='1");
        });

        // Test valid search terms are accepted
        assertDoesNotThrow(() -> {
            ValidationUtils.validateSearchTerm("apple");
        });

        assertDoesNotThrow(() -> {
            ValidationUtils.validateSearchTerm("fresh fruits");
        });
    }
}
