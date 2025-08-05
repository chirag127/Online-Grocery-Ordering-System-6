package com.grocery.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main application class for the Online Grocery Ordering System.
 * This class serves as the entry point for the Spring Boot application.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@SpringBootApplication
public class GroceryOrderingSystemApplication extends SpringBootServletInitializer {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GroceryOrderingSystemApplication.class, args);
    }

    /**
     * Configure the application for deployment as a WAR file.
     * 
     * @param application the Spring application builder
     * @return the configured application builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GroceryOrderingSystemApplication.class);
    }
}
