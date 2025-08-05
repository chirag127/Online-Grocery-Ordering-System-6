# Online Grocery Ordering System - User Manual

**Version:** 1.0.0  
**Date:** August 5, 2025  
**Author:** Chirag Singhal  

## Table of Contents

1. [Introduction](#introduction)
2. [System Requirements](#system-requirements)
3. [Getting Started](#getting-started)
4. [Customer Guide](#customer-guide)
5. [Admin Guide](#admin-guide)
6. [Troubleshooting](#troubleshooting)
7. [Support](#support)

## 1. Introduction

The Online Grocery Ordering System is a comprehensive web application that allows customers to browse and order groceries online while providing administrators with tools to manage products, orders, and customers.

### Key Features
- **Customer Registration & Login**
- **Product Browsing & Search**
- **Shopping Cart & Order Management**
- **Admin Dashboard**
- **Secure Payment Processing**
- **Order Tracking**

## 2. System Requirements

### Minimum Requirements
- **Browser:** Chrome 90+, Firefox 88+, Safari 14+, Edge 90+
- **Internet Connection:** Broadband recommended
- **Screen Resolution:** 1024x768 minimum
- **JavaScript:** Must be enabled

### Recommended Requirements
- **Browser:** Latest version of Chrome, Firefox, Safari, or Edge
- **Internet Connection:** High-speed broadband
- **Screen Resolution:** 1920x1080 or higher

## 3. Getting Started

### Accessing the System
1. Open your web browser
2. Navigate to: `http://localhost:4200` (for local development)
3. The home page will display featured products and navigation options

### Creating an Account
1. Click **"Register"** in the top navigation
2. Fill in the registration form:
   - Full Name
   - Email Address
   - Password (minimum 8 characters)
   - Confirm Password
   - Address
   - Contact Number (10 digits)
3. Click **"Create Account"**
4. You will be redirected to the login page

### Logging In
1. Click **"Login"** in the top navigation
2. Enter your email and password
3. Click **"Sign In"**
4. You will be redirected to the dashboard

## 4. Customer Guide

### 4.1 Dashboard Overview
After logging in, you'll see:
- **Welcome message** with your name
- **Quick navigation** to products and orders
- **Recent orders** summary
- **Featured products**

### 4.2 Browsing Products
1. Click **"Products"** in the navigation menu
2. Browse products by:
   - **Categories:** Fruits, Vegetables, Dairy, Bakery, etc.
   - **Search:** Use the search bar to find specific items
   - **Price range:** Filter by price
3. Each product shows:
   - Product image
   - Name and description
   - Price per unit
   - Available quantity
   - Add to cart button

### 4.3 Adding Items to Cart
1. On any product page, select quantity
2. Click **"Add to Cart"**
3. View cart by clicking the cart icon
4. In cart, you can:
   - Update quantities
   - Remove items
   - View total price
   - Proceed to checkout

### 4.4 Placing an Order
1. From your cart, click **"Proceed to Checkout"**
2. Review your order details
3. Confirm delivery address
4. Select payment method
5. Click **"Place Order"**
6. You'll receive an order confirmation with tracking number

### 4.5 Order Management
1. Go to **"My Orders"** from the dashboard
2. View all your orders with:
   - Order number
   - Date placed
   - Status (Pending, Confirmed, Processing, Shipped, Delivered)
   - Total amount
3. Click on any order to view detailed information

### 4.6 Profile Management
1. Click **"Profile"** in the user menu
2. Update your information:
   - Name
   - Email
   - Address
   - Contact number
3. Change password if needed
4. Click **"Save Changes"**

## 5. Admin Guide

### 5.1 Admin Login
1. Navigate to: `http://localhost:8080/jsp/admin-login.jsp`
2. Enter admin credentials:
   - Username: `admin`
   - Password: `admin123`
3. Click **"Login"**

### 5.2 Admin Dashboard
The admin dashboard provides:
- **System statistics** (total customers, products, orders)
- **Recent orders** requiring attention
- **Low stock alerts**
- **Quick action buttons**

### 5.3 Product Management
1. Click **"Manage Products"**
2. **Add New Product:**
   - Click **"Add Product"**
   - Fill in product details
   - Upload product image
   - Set price and quantity
   - Click **"Save"**
3. **Edit Product:**
   - Click **"Edit"** next to any product
   - Update information
   - Click **"Update"**
4. **Delete Product:**
   - Click **"Delete"** next to any product
   - Confirm deletion

### 5.4 Order Management
1. Click **"Manage Orders"**
2. View all orders with filters:
   - Status
   - Date range
   - Customer
3. **Update Order Status:**
   - Click on an order
   - Change status from dropdown
   - Add notes if needed
   - Click **"Update"**

### 5.5 Customer Management
1. Click **"Manage Customers"**
2. View all registered customers
3. **Customer Actions:**
   - View customer details
   - View order history
   - Activate/Deactivate accounts
   - Send notifications

### 5.6 Reports and Analytics
1. Click **"Reports"**
2. Generate reports for:
   - Sales by date range
   - Top-selling products
   - Customer activity
   - Revenue analysis
3. Export reports as PDF or Excel

## 6. Troubleshooting

### Common Issues

#### Login Problems
**Issue:** Cannot log in with correct credentials
**Solution:**
1. Check if Caps Lock is on
2. Clear browser cache and cookies
3. Try password reset
4. Contact support if issue persists

#### Cart Issues
**Issue:** Items not adding to cart
**Solution:**
1. Refresh the page
2. Check if product is in stock
3. Clear browser cache
4. Try a different browser

#### Order Placement Issues
**Issue:** Cannot complete order
**Solution:**
1. Verify all required fields are filled
2. Check delivery address format
3. Ensure payment method is valid
4. Contact support for assistance

#### Page Loading Issues
**Issue:** Pages loading slowly or not at all
**Solution:**
1. Check internet connection
2. Clear browser cache
3. Disable browser extensions
4. Try incognito/private mode

### Error Messages

| Error Code | Message | Solution |
|------------|---------|----------|
| 400 | Bad Request | Check form inputs for errors |
| 401 | Unauthorized | Log in again |
| 403 | Forbidden | Contact admin for permissions |
| 404 | Page Not Found | Check URL or navigate from menu |
| 500 | Server Error | Try again later or contact support |

## 7. Support

### Contact Information
- **Email:** support@freshmart.com
- **Phone:** +1 (555) 123-4567
- **Hours:** 24/7 Customer Support

### Self-Help Resources
- **FAQ:** Available on the website
- **Video Tutorials:** Step-by-step guides
- **Community Forum:** User discussions and tips

### Reporting Issues
When reporting issues, please include:
1. Your browser and version
2. Steps to reproduce the problem
3. Error messages (if any)
4. Screenshots (if applicable)

---

**© 2025 FreshMart Online Grocery System. All rights reserved.**
