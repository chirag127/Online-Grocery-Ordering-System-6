package com.grocery.ordering.controller;

import com.grocery.ordering.dto.CustomerDTO;
import com.grocery.ordering.dto.OrderDTO;
import com.grocery.ordering.dto.ProductDTO;
import com.grocery.ordering.service.CustomerService;
import com.grocery.ordering.service.OrderService;
import com.grocery.ordering.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for JSP pages.
 * Handles server-side rendering for the grocery ordering system.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Controller
@RequestMapping("/jsp")
public class JspController {

    private static final Logger logger = LoggerFactory.getLogger(JspController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    /**
     * Display login page.
     * 
     * @return login JSP page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Display admin login page.
     * 
     * @return admin login JSP page
     */
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    /**
     * Display customer registration page.
     * 
     * @return registration JSP page
     */
    @GetMapping("/register")
    public String registrationPage() {
        return "register";
    }

    /**
     * Display admin dashboard.
     * 
     * @param model the model for JSP
     * @return admin dashboard JSP page
     */
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        try {
            List<CustomerDTO> customers = customerService.getAllActiveCustomers();
            List<ProductDTO> products = productService.getAllActiveProducts();
            List<OrderDTO> orders = orderService.getAllOrders();
            
            model.addAttribute("customerCount", customers.size());
            model.addAttribute("productCount", products.size());
            model.addAttribute("orderCount", orders.size());
            model.addAttribute("recentOrders", orders.subList(0, Math.min(5, orders.size())));
            
        } catch (Exception e) {
            logger.error("Error loading admin dashboard", e);
            model.addAttribute("error", "Failed to load dashboard data");
        }
        
        return "admin-dashboard";
    }

    /**
     * Display customer management page.
     * 
     * @param model the model for JSP
     * @return customer management JSP page
     */
    @GetMapping("/admin/customers")
    public String customerManagement(Model model) {
        try {
            List<CustomerDTO> customers = customerService.getAllActiveCustomers();
            model.addAttribute("customers", customers);
            
        } catch (Exception e) {
            logger.error("Error loading customers", e);
            model.addAttribute("error", "Failed to load customers");
        }
        
        return "customer-management";
    }

    /**
     * Display customer search page.
     * 
     * @param customerName the customer name to search for
     * @param model the model for JSP
     * @return customer search JSP page
     */
    @GetMapping("/admin/customers/search")
    public String searchCustomers(@RequestParam(required = false) String customerName, Model model) {
        if (customerName != null && !customerName.trim().isEmpty()) {
            try {
                List<CustomerDTO> customers = customerService.searchCustomersByName(customerName);
                model.addAttribute("customers", customers);
                model.addAttribute("searchTerm", customerName);
                
            } catch (Exception e) {
                logger.error("Error searching customers", e);
                model.addAttribute("error", e.getMessage());
                model.addAttribute("searchTerm", customerName);
            }
        }
        
        return "customer-search";
    }

    /**
     * Display product management page.
     * 
     * @param model the model for JSP
     * @return product management JSP page
     */
    @GetMapping("/admin/products")
    public String productManagement(Model model) {
        try {
            List<ProductDTO> products = productService.getAllActiveProducts();
            List<String> categories = productService.getAllCategories();
            
            model.addAttribute("products", products);
            model.addAttribute("categories", categories);
            
        } catch (Exception e) {
            logger.error("Error loading products", e);
            model.addAttribute("error", "Failed to load products");
        }
        
        return "product-management";
    }

    /**
     * Display product search page.
     * 
     * @param productName the product name to search for
     * @param model the model for JSP
     * @return product search JSP page
     */
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) String productName, Model model) {
        if (productName != null && !productName.trim().isEmpty()) {
            try {
                List<ProductDTO> products = productService.searchProductsByName(productName);
                model.addAttribute("products", products);
                model.addAttribute("searchTerm", productName);
                
            } catch (Exception e) {
                logger.error("Error searching products", e);
                model.addAttribute("error", e.getMessage());
                model.addAttribute("searchTerm", productName);
            }
        }
        
        return "product-search";
    }

    /**
     * Display order management page.
     * 
     * @param model the model for JSP
     * @return order management JSP page
     */
    @GetMapping("/admin/orders")
    public String orderManagement(Model model) {
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            model.addAttribute("orders", orders);
            
        } catch (Exception e) {
            logger.error("Error loading orders", e);
            model.addAttribute("error", "Failed to load orders");
        }
        
        return "order-management";
    }

    /**
     * Display customer dashboard.
     * 
     * @param customerId the customer ID
     * @param model the model for JSP
     * @return customer dashboard JSP page
     */
    @GetMapping("/customer/dashboard")
    public String customerDashboard(@RequestParam(required = false) Long customerId, Model model) {
        if (customerId != null) {
            try {
                CustomerDTO customer = customerService.getCustomerById(customerId);
                List<OrderDTO> orders = orderService.getCustomerOrderDetails(customerId);
                List<ProductDTO> products = productService.getInStockProducts();
                
                model.addAttribute("customer", customer);
                model.addAttribute("orders", orders);
                model.addAttribute("products", products.subList(0, Math.min(10, products.size())));
                
            } catch (Exception e) {
                logger.error("Error loading customer dashboard", e);
                model.addAttribute("error", "Failed to load dashboard data");
            }
        }
        
        return "customer-dashboard";
    }

    /**
     * Display home page.
     * 
     * @param model the model for JSP
     * @return home JSP page
     */
    @GetMapping("/")
    public String homePage(Model model) {
        try {
            List<ProductDTO> featuredProducts = productService.getInStockProducts();
            List<String> categories = productService.getAllCategories();
            
            model.addAttribute("featuredProducts", featuredProducts.subList(0, Math.min(8, featuredProducts.size())));
            model.addAttribute("categories", categories);
            
        } catch (Exception e) {
            logger.error("Error loading home page", e);
            model.addAttribute("error", "Failed to load products");
        }
        
        return "home";
    }

    /**
     * Display menu page with options.
     * 
     * @return menu JSP page
     */
    @GetMapping("/menu")
    public String menuPage() {
        return "menu";
    }

    /**
     * Display error page.
     * 
     * @param model the model for JSP
     * @return error JSP page
     */
    @GetMapping("/error")
    public String errorPage(Model model) {
        model.addAttribute("errorMessage", "An error occurred. Please try again.");
        return "error";
    }
}
