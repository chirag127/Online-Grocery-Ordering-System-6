<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Menu - Online Grocery Ordering System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 2rem 0;
        }
        .menu-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .menu-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem;
            text-align: center;
        }
        .menu-content {
            padding: 2rem;
        }
        .menu-option {
            background: #f8f9fa;
            border: 2px solid #e9ecef;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1rem;
            transition: all 0.3s ease;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
            display: block;
        }
        .menu-option:hover {
            background: #e9ecef;
            border-color: #667eea;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            color: inherit;
            text-decoration: none;
        }
        .menu-option i {
            font-size: 2rem;
            color: #667eea;
            margin-bottom: 1rem;
        }
        .menu-option h5 {
            color: #333;
            margin-bottom: 0.5rem;
        }
        .menu-option p {
            color: #6c757d;
            margin-bottom: 0;
        }
        .exit-btn {
            background: linear-gradient(135deg, #dc3545 0%, #fd7e14 100%);
            border: none;
            border-radius: 10px;
            padding: 12px 30px;
            font-weight: 600;
            color: white;
        }
        .exit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        .alert {
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8">
                <div class="menu-container">
                    <div class="menu-header">
                        <h2><i class="fas fa-cogs me-2"></i>Admin Control Panel</h2>
                        <p class="mb-0">Select an option to manage the grocery ordering system</p>
                    </div>
                    
                    <div class="menu-content">
                        <div id="alertContainer"></div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/register" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-user-plus"></i>
                                        <h5>1. Customer Registration</h5>
                                        <p>Register new customers in the system</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/admin/customers" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-user-edit"></i>
                                        <h5>2. Update Customer Details</h5>
                                        <p>Modify existing customer information</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/admin/orders" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-shopping-bag"></i>
                                        <h5>3. Get Customer Order Details</h5>
                                        <p>View and manage customer orders</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/admin/customers/search" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-search"></i>
                                        <h5>4. Customer Search</h5>
                                        <p>Search customers by name</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/products/search" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-search-plus"></i>
                                        <h5>5. Product Search</h5>
                                        <p>Search products by name</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/admin/products" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-plus-circle"></i>
                                        <h5>6. Register Product</h5>
                                        <p>Add new products to inventory</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/admin/products" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-edit"></i>
                                        <h5>7. Update Product</h5>
                                        <p>Modify existing product details</p>
                                    </div>
                                </a>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <a href="/jsp/admin/products" class="menu-option">
                                    <div class="text-center">
                                        <i class="fas fa-trash-alt"></i>
                                        <h5>8. Delete Product</h5>
                                        <p>Remove products from inventory</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                        
                        <div class="text-center mt-4">
                            <button class="btn exit-btn" onclick="exitSystem()">
                                <i class="fas fa-sign-out-alt me-2"></i>9. Exit System
                            </button>
                        </div>
                        
                        <div class="text-center mt-3">
                            <a href="/jsp/admin/dashboard" class="btn btn-outline-primary">
                                <i class="fas fa-tachometer-alt me-2"></i>Admin Dashboard
                            </a>
                            <a href="/jsp/" class="btn btn-outline-secondary ms-2">
                                <i class="fas fa-home me-2"></i>Home
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function exitSystem() {
            if (confirm('Are you sure you want to exit the system?')) {
                // Clear any stored authentication data
                localStorage.removeItem('token');
                localStorage.removeItem('userRole');
                localStorage.removeItem('userId');
                
                showAlert('Good Bye User! Terminating the Program.', 'info');
                
                setTimeout(() => {
                    window.location.href = '/jsp/';
                }, 2000);
            }
        }
        
        function showAlert(message, type) {
            const alertContainer = document.getElementById('alertContainer');
            alertContainer.innerHTML = `
                <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                    ${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            `;
        }
        
        // Handle invalid option selection
        document.addEventListener('click', function(e) {
            if (e.target.classList.contains('menu-option') && !e.target.href) {
                e.preventDefault();
                showAlert('You have selected an inappropriate option. Kindly select an appropriate option.', 'warning');
            }
        });
    </script>
</body>
</html>
