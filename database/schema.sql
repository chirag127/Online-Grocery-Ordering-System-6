-- Online Grocery Ordering System Database Schema
-- Author: Chirag Singhal
-- Version: 1.0.0

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS grocery_ordering_system;
USE grocery_ordering_system;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS admin_users;

-- Create admin_users table
CREATE TABLE admin_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'ADMIN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Create customers table
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    contact_number VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    
    -- Constraints
    CONSTRAINT chk_contact_number CHECK (contact_number REGEXP '^[0-9]{10}$')
);

-- Create products table
CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    description TEXT,
    category VARCHAR(50),
    image_url VARCHAR(255),
    is_reserved BOOLEAN DEFAULT FALSE,
    reserved_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    
    -- Constraints
    CONSTRAINT chk_price CHECK (price >= 0),
    CONSTRAINT chk_quantity CHECK (quantity >= 0),
    FOREIGN KEY (reserved_by) REFERENCES customers(customer_id) ON DELETE SET NULL
);

-- Create orders table
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    order_status VARCHAR(20) DEFAULT 'PENDING',
    delivery_address TEXT NOT NULL,
    contact_number VARCHAR(10) NOT NULL,
    
    -- Constraints
    CONSTRAINT chk_total_amount CHECK (total_amount >= 0),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- Create order_items table
CREATE TABLE order_items (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    
    -- Constraints
    CONSTRAINT chk_item_quantity CHECK (quantity > 0),
    CONSTRAINT chk_unit_price CHECK (unit_price >= 0),
    CONSTRAINT chk_total_price CHECK (total_price >= 0),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- Insert default admin user
INSERT INTO admin_users (username, password, email, full_name) VALUES 
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'admin@grocery.com', 'System Administrator');

-- Insert sample products
INSERT INTO products (product_name, price, quantity, description, category) VALUES
('Apples', 150.00, 100, 'Fresh red apples', 'Fruits'),
('Bananas', 80.00, 150, 'Fresh yellow bananas', 'Fruits'),
('Milk', 60.00, 50, 'Fresh dairy milk 1L', 'Dairy'),
('Bread', 40.00, 75, 'Whole wheat bread', 'Bakery'),
('Rice', 120.00, 200, 'Basmati rice 1kg', 'Grains'),
('Chicken', 300.00, 30, 'Fresh chicken 1kg', 'Meat'),
('Tomatoes', 50.00, 80, 'Fresh tomatoes', 'Vegetables'),
('Onions', 40.00, 120, 'Fresh onions', 'Vegetables'),
('Potatoes', 35.00, 150, 'Fresh potatoes', 'Vegetables'),
('Eggs', 180.00, 60, 'Fresh eggs dozen', 'Dairy');

-- Create indexes for better performance
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_products_name ON products(product_name);
CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_product ON order_items(product_id);
