-- Initialize H2 database with sample data
-- This file is automatically executed by Spring Boot

-- Insert default admin user (password: admin123 - BCrypt hashed)
INSERT INTO admin_users (username, password, email, full_name, role, created_at, updated_at, is_active) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'admin@freshmart.com', 'System Administrator', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Insert sample customers (password: password - BCrypt hashed)
INSERT INTO customers (customer_name, email, password, address, contact_number, created_at, updated_at, is_active) VALUES
('John Doe', 'john.doe@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '123 Main Street, City, State 12345', '9876543210', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Jane Smith', 'jane.smith@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '456 Oak Avenue, City, State 12345', '9876543211', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Bob Johnson', 'bob.johnson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '789 Pine Road, City, State 12345', '9876543212', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Alice Brown', 'alice.brown@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '321 Elm Street, City, State 12345', '9876543213', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Charlie Wilson', 'charlie.wilson@email.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '654 Maple Drive, City, State 12345', '9876543214', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Insert sample products
INSERT INTO products (product_name, price, quantity, description, category, created_at, updated_at, is_active, is_reserved, reserved_by) VALUES
-- Fruits
('Red Apples', 150.00, 100, 'Fresh red apples from local farms', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Green Apples', 160.00, 80, 'Crisp green apples', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Bananas', 80.00, 150, 'Fresh yellow bananas', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Oranges', 120.00, 90, 'Juicy oranges', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Grapes', 200.00, 60, 'Sweet grapes', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Mangoes', 250.00, 40, 'Ripe mangoes', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Strawberries', 300.00, 30, 'Fresh strawberries', 'Fruits', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),

-- Vegetables
('Tomatoes', 50.00, 80, 'Fresh tomatoes', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Onions', 40.00, 120, 'Fresh onions', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Potatoes', 35.00, 150, 'Fresh potatoes', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Carrots', 45.00, 70, 'Fresh carrots', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Broccoli', 80.00, 50, 'Fresh broccoli', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Spinach', 60.00, 40, 'Fresh spinach leaves', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Bell Peppers', 90.00, 60, 'Colorful bell peppers', 'Vegetables', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),

-- Dairy
('Milk', 60.00, 50, 'Fresh dairy milk 1L', 'Dairy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Cheese', 200.00, 30, 'Fresh cheese block', 'Dairy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Yogurt', 80.00, 40, 'Greek yogurt', 'Dairy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Butter', 150.00, 25, 'Fresh butter', 'Dairy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Eggs', 180.00, 60, 'Fresh eggs dozen', 'Dairy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),

-- Bakery
('Bread', 40.00, 75, 'Whole wheat bread', 'Bakery', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Croissants', 120.00, 20, 'Fresh croissants', 'Bakery', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Bagels', 100.00, 30, 'Fresh bagels', 'Bakery', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Muffins', 80.00, 25, 'Blueberry muffins', 'Bakery', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),

-- Grains
('Rice', 120.00, 200, 'Basmati rice 1kg', 'Grains', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Wheat Flour', 80.00, 150, 'Whole wheat flour 1kg', 'Grains', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Oats', 150.00, 100, 'Rolled oats 500g', 'Grains', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Quinoa', 300.00, 50, 'Organic quinoa 500g', 'Grains', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),

-- Meat
('Chicken', 300.00, 30, 'Fresh chicken 1kg', 'Meat', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Fish', 400.00, 20, 'Fresh fish 1kg', 'Meat', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Mutton', 600.00, 15, 'Fresh mutton 1kg', 'Meat', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),

-- Beverages
('Orange Juice', 100.00, 40, 'Fresh orange juice 1L', 'Beverages', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Apple Juice', 110.00, 35, 'Fresh apple juice 1L', 'Beverages', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Green Tea', 200.00, 50, 'Organic green tea', 'Beverages', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null),
('Coffee', 250.00, 30, 'Premium coffee beans', 'Beverages', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false, null);

-- Insert sample orders
INSERT INTO orders (customer_id, total_amount, order_status, delivery_address, contact_number, order_date) VALUES
(1, 450.00, 'DELIVERED', '123 Main Street, City, State 12345', '9876543210', CURRENT_TIMESTAMP),
(2, 320.00, 'SHIPPED', '456 Oak Avenue, City, State 12345', '9876543211', CURRENT_TIMESTAMP),
(3, 280.00, 'PROCESSING', '789 Pine Road, City, State 12345', '9876543212', CURRENT_TIMESTAMP),
(1, 150.00, 'PENDING', '123 Main Street, City, State 12345', '9876543210', CURRENT_TIMESTAMP),
(4, 520.00, 'CONFIRMED', '321 Elm Street, City, State 12345', '9876543213', CURRENT_TIMESTAMP);

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, quantity, unit_price, total_price) VALUES
-- Order 1 items
(1, 1, 2, 150.00, 300.00),
(1, 8, 3, 50.00, 150.00),

-- Order 2 items
(2, 3, 4, 80.00, 320.00),

-- Order 3 items
(3, 20, 2, 40.00, 80.00),
(3, 15, 1, 200.00, 200.00),

-- Order 4 items
(4, 1, 1, 150.00, 150.00),

-- Order 5 items
(5, 28, 1, 300.00, 300.00),
(5, 21, 1, 120.00, 120.00),
(5, 22, 1, 100.00, 100.00);
