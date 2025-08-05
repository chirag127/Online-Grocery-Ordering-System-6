-- Initialize database with sample data
-- Run this script after creating the database schema

USE grocery_ordering_system;

-- Insert default admin user (password: admin123)
INSERT INTO admin_users (username, password, email, full_name, role) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'admin@freshmart.com', 'System Administrator', 'ADMIN');

-- Insert sample customers (passwords are BCrypt hashed)
INSERT INTO customers (customer_name, email, password, address, contact_number) VALUES
('John Doe', 'john.doe@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '123 Main Street, City, State 12345', '9876543210'),
('Jane Smith', 'jane.smith@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '456 Oak Avenue, City, State 12345', '9876543211'),
('Bob Johnson', 'bob.johnson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '789 Pine Road, City, State 12345', '9876543212'),
('Alice Brown', 'alice.brown@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '321 Elm Street, City, State 12345', '9876543213'),
('Charlie Wilson', 'charlie.wilson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '654 Maple Drive, City, State 12345', '9876543214');

-- Insert more sample products
INSERT INTO products (product_name, price, quantity, description, category) VALUES
-- Fruits
('Red Apples', 150.00, 100, 'Fresh red apples from local farms', 'Fruits'),
('Green Apples', 160.00, 80, 'Crisp green apples', 'Fruits'),
('Bananas', 80.00, 150, 'Fresh yellow bananas', 'Fruits'),
('Oranges', 120.00, 90, 'Juicy oranges', 'Fruits'),
('Grapes', 200.00, 60, 'Sweet grapes', 'Fruits'),
('Mangoes', 250.00, 40, 'Ripe mangoes', 'Fruits'),
('Strawberries', 300.00, 30, 'Fresh strawberries', 'Fruits'),

-- Vegetables
('Tomatoes', 50.00, 80, 'Fresh tomatoes', 'Vegetables'),
('Onions', 40.00, 120, 'Fresh onions', 'Vegetables'),
('Potatoes', 35.00, 150, 'Fresh potatoes', 'Vegetables'),
('Carrots', 45.00, 70, 'Fresh carrots', 'Vegetables'),
('Broccoli', 80.00, 50, 'Fresh broccoli', 'Vegetables'),
('Spinach', 60.00, 40, 'Fresh spinach leaves', 'Vegetables'),
('Bell Peppers', 90.00, 60, 'Colorful bell peppers', 'Vegetables'),

-- Dairy
('Milk', 60.00, 50, 'Fresh dairy milk 1L', 'Dairy'),
('Cheese', 200.00, 30, 'Fresh cheese block', 'Dairy'),
('Yogurt', 80.00, 40, 'Greek yogurt', 'Dairy'),
('Butter', 150.00, 25, 'Fresh butter', 'Dairy'),
('Eggs', 180.00, 60, 'Fresh eggs dozen', 'Dairy'),

-- Bakery
('Bread', 40.00, 75, 'Whole wheat bread', 'Bakery'),
('Croissants', 120.00, 20, 'Fresh croissants', 'Bakery'),
('Bagels', 100.00, 30, 'Fresh bagels', 'Bakery'),
('Muffins', 80.00, 25, 'Blueberry muffins', 'Bakery'),

-- Grains
('Rice', 120.00, 200, 'Basmati rice 1kg', 'Grains'),
('Wheat Flour', 80.00, 150, 'Whole wheat flour 1kg', 'Grains'),
('Oats', 150.00, 100, 'Rolled oats 500g', 'Grains'),
('Quinoa', 300.00, 50, 'Organic quinoa 500g', 'Grains'),

-- Meat
('Chicken', 300.00, 30, 'Fresh chicken 1kg', 'Meat'),
('Fish', 400.00, 20, 'Fresh fish 1kg', 'Meat'),
('Mutton', 600.00, 15, 'Fresh mutton 1kg', 'Meat'),

-- Beverages
('Orange Juice', 100.00, 40, 'Fresh orange juice 1L', 'Beverages'),
('Apple Juice', 110.00, 35, 'Fresh apple juice 1L', 'Beverages'),
('Green Tea', 200.00, 50, 'Organic green tea', 'Beverages'),
('Coffee', 250.00, 30, 'Premium coffee beans', 'Beverages');

-- Insert sample orders
INSERT INTO orders (customer_id, total_amount, order_status, delivery_address, contact_number) VALUES
(1, 450.00, 'DELIVERED', '123 Main Street, City, State 12345', '9876543210'),
(2, 320.00, 'SHIPPED', '456 Oak Avenue, City, State 12345', '9876543211'),
(3, 280.00, 'PROCESSING', '789 Pine Road, City, State 12345', '9876543212'),
(1, 150.00, 'PENDING', '123 Main Street, City, State 12345', '9876543210'),
(4, 520.00, 'CONFIRMED', '321 Elm Street, City, State 12345', '9876543213');

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price) VALUES
-- Order 1 items
(1, 1, 2, 150.00, 300.00),
(1, 8, 3, 50.00, 150.00),

-- Order 2 items
(2, 3, 4, 80.00, 320.00),

-- Order 3 items
(3, 11, 2, 40.00, 80.00),
(3, 13, 1, 200.00, 200.00),

-- Order 4 items
(4, 1, 1, 150.00, 150.00),

-- Order 5 items
(5, 20, 1, 300.00, 300.00),
(5, 14, 1, 120.00, 120.00),
(5, 18, 1, 100.00, 100.00);

-- Update product quantities based on orders
UPDATE products SET quantity = quantity - 2 WHERE product_id = 1;
UPDATE products SET quantity = quantity - 3 WHERE product_id = 8;
UPDATE products SET quantity = quantity - 4 WHERE product_id = 3;
UPDATE products SET quantity = quantity - 2 WHERE product_id = 11;
UPDATE products SET quantity = quantity - 1 WHERE product_id = 13;
UPDATE products SET quantity = quantity - 1 WHERE product_id = 20;
UPDATE products SET quantity = quantity - 1 WHERE product_id = 14;
UPDATE products SET quantity = quantity - 1 WHERE product_id = 18;
