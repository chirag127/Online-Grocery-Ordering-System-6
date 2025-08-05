import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';

/**
 * Home page component displaying featured products and categories.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <!-- Hero Section -->
    <section class="hero-section">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-6">
            <h1 class="display-4 fw-bold mb-4 text-white">
              Fresh Groceries Delivered to Your Door
            </h1>
            <p class="lead mb-4 text-light">
              Shop from our wide selection of fresh fruits, vegetables, dairy products, and more. 
              Get everything you need with just a few clicks!
            </p>
            <div class="d-flex gap-3 flex-wrap">
              <a routerLink="/register" class="btn btn-light btn-lg">
                <i class="fas fa-user-plus me-2"></i>Get Started
              </a>
              <a routerLink="/products/search" class="btn btn-outline-light btn-lg">
                <i class="fas fa-search me-2"></i>Browse Products
              </a>
            </div>
          </div>
          <div class="col-lg-6 text-center">
            <i class="fas fa-shopping-basket hero-icon"></i>
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
            <div class="card card-custom h-100 text-center p-4">
              <div class="card-body">
                <i class="fas fa-leaf text-success mb-3 feature-icon"></i>
                <h5 class="card-title">Fresh & Organic</h5>
                <p class="card-text">
                  Hand-picked fresh produce and organic products delivered straight from farms to your table.
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4 mb-4">
            <div class="card card-custom h-100 text-center p-4">
              <div class="card-body">
                <i class="fas fa-truck text-primary mb-3 feature-icon"></i>
                <h5 class="card-title">Fast Delivery</h5>
                <p class="card-text">
                  Quick and reliable delivery service. Get your groceries delivered within hours of ordering.
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4 mb-4">
            <div class="card card-custom h-100 text-center p-4">
              <div class="card-body">
                <i class="fas fa-shield-alt text-warning mb-3 feature-icon"></i>
                <h5 class="card-title">Secure Shopping</h5>
                <p class="card-text">
                  Safe and secure payment processing with advanced encryption to protect your data.
                </p>
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
            <div class="category-container">
              <span *ngFor="let category of categories" class="category-badge">
                <i class="fas fa-tag me-1"></i>{{ category }}
              </span>
              <span *ngIf="categories.length === 0" class="category-badge">
                <i class="fas fa-tag me-1"></i>Fruits
              </span>
              <span *ngIf="categories.length === 0" class="category-badge">
                <i class="fas fa-tag me-1"></i>Vegetables
              </span>
              <span *ngIf="categories.length === 0" class="category-badge">
                <i class="fas fa-tag me-1"></i>Dairy
              </span>
              <span *ngIf="categories.length === 0" class="category-badge">
                <i class="fas fa-tag me-1"></i>Bakery
              </span>
              <span *ngIf="categories.length === 0" class="category-badge">
                <i class="fas fa-tag me-1"></i>Meat
              </span>
              <span *ngIf="categories.length === 0" class="category-badge">
                <i class="fas fa-tag me-1"></i>Grains
              </span>
            </div>
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
        
        <!-- Loading State -->
        <div *ngIf="loading" class="text-center">
          <div class="spinner-custom mx-auto"></div>
          <p class="mt-3">Loading products...</p>
        </div>
        
        <!-- Products Grid -->
        <div *ngIf="!loading" class="row">
          <div *ngFor="let product of featuredProducts; let i = index" 
               class="col-md-3 col-sm-6 mb-4" 
               [class.d-none]="i >= 8">
            <div class="card product-card h-100">
              <div class="card-body text-center">
                <i class="fas fa-apple-alt text-success mb-3 product-icon"></i>
                <h6 class="card-title">{{ product.productName }}</h6>
                <p class="product-price">₹{{ product.price | number:'1.2-2' }}</p>
                <p class="text-muted small">{{ product.category || 'General' }}</p>
                <span class="badge" 
                      [class.bg-success]="product.quantity > 0"
                      [class.bg-danger]="product.quantity <= 0">
                  {{ product.quantity > 0 ? 'In Stock' : 'Out of Stock' }}
                </span>
              </div>
            </div>
          </div>
          
          <!-- Default Products if none loaded -->
          <ng-container *ngIf="featuredProducts.length === 0">
            <div class="col-md-3 col-sm-6 mb-4">
              <div class="card product-card h-100">
                <div class="card-body text-center">
                  <i class="fas fa-apple-alt text-success mb-3 product-icon"></i>
                  <h6 class="card-title">Fresh Apples</h6>
                  <p class="product-price">₹150.00</p>
                  <p class="text-muted small">Fruits</p>
                  <span class="badge bg-success">In Stock</span>
                </div>
              </div>
            </div>
            <div class="col-md-3 col-sm-6 mb-4">
              <div class="card product-card h-100">
                <div class="card-body text-center">
                  <i class="fas fa-seedling text-warning mb-3 product-icon"></i>
                  <h6 class="card-title">Fresh Bananas</h6>
                  <p class="product-price">₹80.00</p>
                  <p class="text-muted small">Fruits</p>
                  <span class="badge bg-success">In Stock</span>
                </div>
              </div>
            </div>
            <div class="col-md-3 col-sm-6 mb-4">
              <div class="card product-card h-100">
                <div class="card-body text-center">
                  <i class="fas fa-cheese text-warning mb-3 product-icon"></i>
                  <h6 class="card-title">Fresh Milk</h6>
                  <p class="product-price">₹60.00</p>
                  <p class="text-muted small">Dairy</p>
                  <span class="badge bg-success">In Stock</span>
                </div>
              </div>
            </div>
            <div class="col-md-3 col-sm-6 mb-4">
              <div class="card product-card h-100">
                <div class="card-body text-center">
                  <i class="fas fa-bread-slice text-warning mb-3 product-icon"></i>
                  <h6 class="card-title">Whole Wheat Bread</h6>
                  <p class="product-price">₹40.00</p>
                  <p class="text-muted small">Bakery</p>
                  <span class="badge bg-success">In Stock</span>
                </div>
              </div>
            </div>
          </ng-container>
        </div>
        
        <div class="text-center mt-4">
          <a routerLink="/products" class="btn btn-primary-custom btn-lg">
            <i class="fas fa-eye me-2"></i>View All Products
          </a>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="py-5 bg-primary-custom text-white">
      <div class="container text-center">
        <h2 class="fw-bold mb-4">Ready to Start Shopping?</h2>
        <p class="lead mb-4">
          Join thousands of satisfied customers who trust FreshMart for their grocery needs.
        </p>
        <div class="d-flex justify-content-center gap-3 flex-wrap">
          <a routerLink="/register" class="btn btn-light btn-lg">
            <i class="fas fa-user-plus me-2"></i>Create Account
          </a>
          <a routerLink="/admin/login" class="btn btn-outline-light btn-lg">
            <i class="fas fa-cogs me-2"></i>Admin Panel
          </a>
        </div>
      </div>
    </section>
  `,
  styles: [`
    .hero-section {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      padding: 4rem 0;
      min-height: 60vh;
      display: flex;
      align-items: center;
    }
    
    .hero-icon {
      font-size: 15rem;
      opacity: 0.3;
      color: white;
    }
    
    .feature-icon {
      font-size: 3rem;
    }
    
    .product-icon {
      font-size: 3rem;
    }
    
    .category-badge {
      background: #f8f9fa;
      border: 1px solid #dee2e6;
      border-radius: 20px;
      padding: 8px 16px;
      margin: 4px;
      display: inline-block;
      color: #495057;
      transition: all 0.3s ease;
      cursor: pointer;
    }
    
    .category-badge:hover {
      background: #e9ecef;
      transform: translateY(-2px);
    }
    
    .category-container {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      gap: 8px;
    }
    
    @media (max-width: 768px) {
      .hero-section {
        padding: 2rem 0;
        text-align: center;
      }
      
      .hero-icon {
        font-size: 8rem;
        margin-top: 2rem;
      }
      
      .display-4 {
        font-size: 2rem;
      }
      
      .btn-lg {
        width: 100%;
        margin-bottom: 1rem;
      }
    }
  `]
})
export class HomeComponent implements OnInit {
  featuredProducts: Product[] = [];
  categories: string[] = [];
  loading = true;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadFeaturedProducts();
    this.loadCategories();
  }

  private loadFeaturedProducts(): void {
    this.productService.getInStockProducts().subscribe({
      next: (products) => {
        this.featuredProducts = products.slice(0, 8); // Show only first 8 products
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading featured products:', error);
        this.loading = false;
      }
    });
  }

  private loadCategories(): void {
    this.productService.getAllCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
      }
    });
  }
}
