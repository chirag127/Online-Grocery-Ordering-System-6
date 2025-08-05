<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Grocery Ordering System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 4rem 0;
        }
        .feature-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .feature-card:hover {
            transform: translateY(-5px);
        }
        .product-card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .product-card:hover {
            transform: translateY(-3px);
        }
        .btn-primary-custom {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 25px;
            padding: 10px 30px;
        }
        .btn-success-custom {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            border: none;
            border-radius: 25px;
            padding: 10px 30px;
        }
        .category-badge {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 20px;
            padding: 5px 15px;
            margin: 2px;
            display: inline-block;
            color: #495057;
        }
        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
        }
        footer {
            background: #343a40;
            color: white;
            padding: 2rem 0;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/jsp/">
                <i class="fas fa-shopping-cart me-2"></i>FreshMart
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="/jsp/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/jsp/products/search">Products</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/jsp/login">
                            <i class="fas fa-sign-in-alt me-1"></i>Login
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/jsp/register">
                            <i class="fas fa-user-plus me-1"></i>Register
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/jsp/admin/login">
                            <i class="fas fa-user-shield me-1"></i>Admin
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="display-4 fw-bold mb-4">Fresh Groceries Delivered to Your Door</h1>
                    <p class="lead mb-4">Shop from our wide selection of fresh fruits, vegetables, dairy products, and more. Get everything you need with just a few clicks!</p>
                    <div class="d-flex gap-3">
                        <a href="/jsp/register" class="btn btn-light btn-lg">
                            <i class="fas fa-user-plus me-2"></i>Get Started
                        </a>
                        <a href="/jsp/products/search" class="btn btn-outline-light btn-lg">
                            <i class="fas fa-search me-2"></i>Browse Products
                        </a>
                    </div>
                </div>
                <div class="col-lg-6 text-center">
                    <i class="fas fa-shopping-basket" style="font-size: 15rem; opacity: 0.3;"></i>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="py-5">
        <div class="container">
            <div class="row text-center mb-5">
                <div class="col">
                    <h2 class="fw-bold">Why Choose FreshMart?</h2>
                    <p class="text-muted">Experience the best online grocery shopping</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4 mb-4">
                    <div class="card feature-card h-100 text-center p-4">
                        <div class="card-body">
                            <i class="fas fa-leaf text-success mb-3" style="font-size: 3rem;"></i>
                            <h5 class="card-title">Fresh & Organic</h5>
                            <p class="card-text">Hand-picked fresh produce and organic products delivered straight from farms to your table.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card feature-card h-100 text-center p-4">
                        <div class="card-body">
                            <i class="fas fa-truck text-primary mb-3" style="font-size: 3rem;"></i>
                            <h5 class="card-title">Fast Delivery</h5>
                            <p class="card-text">Quick and reliable delivery service. Get your groceries delivered within hours of ordering.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card feature-card h-100 text-center p-4">
                        <div class="card-body">
                            <i class="fas fa-shield-alt text-warning mb-3" style="font-size: 3rem;"></i>
                            <h5 class="card-title">Secure Shopping</h5>
                            <p class="card-text">Safe and secure payment processing with advanced encryption to protect your data.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Categories Section -->
    <section class="py-5 bg-light">
        <div class="container">
            <div class="row text-center mb-5">
                <div class="col">
                    <h2 class="fw-bold">Shop by Category</h2>
                    <p class="text-muted">Explore our wide range of product categories</p>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-8 text-center">
                    <c:forEach var="category" items="${categories}">
                        <span class="category-badge">
                            <i class="fas fa-tag me-1"></i>${category}
                        </span>
                    </c:forEach>
                    <c:if test="${empty categories}">
                        <span class="category-badge"><i class="fas fa-tag me-1"></i>Fruits</span>
                        <span class="category-badge"><i class="fas fa-tag me-1"></i>Vegetables</span>
                        <span class="category-badge"><i class="fas fa-tag me-1"></i>Dairy</span>
                        <span class="category-badge"><i class="fas fa-tag me-1"></i>Bakery</span>
                        <span class="category-badge"><i class="fas fa-tag me-1"></i>Meat</span>
                        <span class="category-badge"><i class="fas fa-tag me-1"></i>Grains</span>
                    </c:if>
                </div>
            </div>
        </div>
    </section>

    <!-- Featured Products Section -->
    <section class="py-5">
        <div class="container">
            <div class="row text-center mb-5">
                <div class="col">
                    <h2 class="fw-bold">Featured Products</h2>
                    <p class="text-muted">Check out our most popular items</p>
                </div>
            </div>
            <div class="row">
                <c:forEach var="product" items="${featuredProducts}" varStatus="status">
                    <c:if test="${status.index < 4}">
                        <div class="col-md-3 mb-4">
                            <div class="card product-card h-100">
                                <div class="card-body text-center">
                                    <i class="fas fa-apple-alt text-success mb-3" style="font-size: 3rem;"></i>
                                    <h6 class="card-title">${product.productName}</h6>
                                    <p class="text-success fw-bold">₹${product.price}</p>
                                    <p class="text-muted small">${product.category}</p>
                                    <span class="badge bg-${product.quantity > 0 ? 'success' : 'danger'}">
                                        ${product.quantity > 0 ? 'In Stock' : 'Out of Stock'}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
                <c:if test="${empty featuredProducts}">
                    <div class="col-md-3 mb-4">
                        <div class="card product-card h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-apple-alt text-success mb-3" style="font-size: 3rem;"></i>
                                <h6 class="card-title">Fresh Apples</h6>
                                <p class="text-success fw-bold">₹150.00</p>
                                <p class="text-muted small">Fruits</p>
                                <span class="badge bg-success">In Stock</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card product-card h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-carrot text-warning mb-3" style="font-size: 3rem;"></i>
                                <h6 class="card-title">Fresh Bananas</h6>
                                <p class="text-success fw-bold">₹80.00</p>
                                <p class="text-muted small">Fruits</p>
                                <span class="badge bg-success">In Stock</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card product-card h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-cheese text-warning mb-3" style="font-size: 3rem;"></i>
                                <h6 class="card-title">Fresh Milk</h6>
                                <p class="text-success fw-bold">₹60.00</p>
                                <p class="text-muted small">Dairy</p>
                                <span class="badge bg-success">In Stock</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-4">
                        <div class="card product-card h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-bread-slice text-warning mb-3" style="font-size: 3rem;"></i>
                                <h6 class="card-title">Whole Wheat Bread</h6>
                                <p class="text-success fw-bold">₹40.00</p>
                                <p class="text-muted small">Bakery</p>
                                <span class="badge bg-success">In Stock</span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="text-center">
                <a href="/jsp/products/search" class="btn btn-primary-custom btn-lg">
                    <i class="fas fa-eye me-2"></i>View All Products
                </a>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="py-5 bg-primary text-white">
        <div class="container text-center">
            <h2 class="fw-bold mb-4">Ready to Start Shopping?</h2>
            <p class="lead mb-4">Join thousands of satisfied customers who trust FreshMart for their grocery needs.</p>
            <div class="d-flex justify-content-center gap-3">
                <a href="/jsp/register" class="btn btn-light btn-lg">
                    <i class="fas fa-user-plus me-2"></i>Create Account
                </a>
                <a href="/jsp/menu" class="btn btn-outline-light btn-lg">
                    <i class="fas fa-cogs me-2"></i>Admin Panel
                </a>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5><i class="fas fa-shopping-cart me-2"></i>FreshMart</h5>
                    <p>Your trusted partner for fresh groceries and quality products.</p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p>&copy; 2025 Online Grocery Ordering System. All rights reserved.</p>
                    <p>Developed by <strong>Chirag Singhal</strong></p>
                </div>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
