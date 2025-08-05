import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/auth.model';

/**
 * Navigation bar component for the application.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
      <div class="container">
        <a class="navbar-brand navbar-brand-custom" routerLink="/">
          <i class="fas fa-shopping-cart me-2"></i>FreshMart
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                data-bs-target="#navbarNav" aria-controls="navbarNav" 
                aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <a class="nav-link" routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
                <i class="fas fa-home me-1"></i>Home
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/products" routerLinkActive="active">
                <i class="fas fa-shopping-basket me-1"></i>Products
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/products/search" routerLinkActive="active">
                <i class="fas fa-search me-1"></i>Search
              </a>
            </li>
          </ul>
          
          <ul class="navbar-nav">
            <!-- Guest Navigation -->
            <ng-container *ngIf="!isAuthenticated">
              <li class="nav-item">
                <a class="nav-link" routerLink="/login" routerLinkActive="active">
                  <i class="fas fa-sign-in-alt me-1"></i>Login
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" routerLink="/register" routerLinkActive="active">
                  <i class="fas fa-user-plus me-1"></i>Register
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" routerLink="/admin/login" routerLinkActive="active">
                  <i class="fas fa-user-shield me-1"></i>Admin
                </a>
              </li>
            </ng-container>
            
            <!-- Authenticated User Navigation -->
            <ng-container *ngIf="isAuthenticated && currentUser">
              <!-- Customer Navigation -->
              <ng-container *ngIf="authService.isCustomer()">
                <li class="nav-item">
                  <a class="nav-link" routerLink="/customer/dashboard" routerLinkActive="active">
                    <i class="fas fa-tachometer-alt me-1"></i>Dashboard
                  </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" routerLink="/customer/orders" routerLinkActive="active">
                    <i class="fas fa-shopping-bag me-1"></i>My Orders
                  </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" routerLink="/customer/profile" routerLinkActive="active">
                    <i class="fas fa-user me-1"></i>Profile
                  </a>
                </li>
              </ng-container>
              
              <!-- Admin Navigation -->
              <ng-container *ngIf="authService.isAdmin()">
                <li class="nav-item">
                  <a class="nav-link" routerLink="/admin/dashboard" routerLinkActive="active">
                    <i class="fas fa-tachometer-alt me-1"></i>Dashboard
                  </a>
                </li>
                <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" id="adminDropdown" 
                     role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="fas fa-cogs me-1"></i>Manage
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="adminDropdown">
                    <li><a class="dropdown-item" routerLink="/admin/customers">
                      <i class="fas fa-users me-2"></i>Customers
                    </a></li>
                    <li><a class="dropdown-item" routerLink="/admin/products">
                      <i class="fas fa-box me-2"></i>Products
                    </a></li>
                    <li><a class="dropdown-item" routerLink="/admin/orders">
                      <i class="fas fa-shopping-cart me-2"></i>Orders
                    </a></li>
                  </ul>
                </li>
              </ng-container>
              
              <!-- User Menu -->
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" 
                   role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <i class="fas fa-user-circle me-1"></i>{{ currentUser.username }}
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                  <li><h6 class="dropdown-header">{{ currentUser.email }}</h6></li>
                  <li><hr class="dropdown-divider"></li>
                  <li *ngIf="authService.isCustomer()">
                    <a class="dropdown-item" routerLink="/customer/profile">
                      <i class="fas fa-user me-2"></i>Profile
                    </a>
                  </li>
                  <li>
                    <a class="dropdown-item" href="#" (click)="logout($event)">
                      <i class="fas fa-sign-out-alt me-2"></i>Logout
                    </a>
                  </li>
                </ul>
              </li>
            </ng-container>
          </ul>
        </div>
      </div>
    </nav>
  `,
  styles: [`
    .navbar-custom {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
    
    .navbar-brand-custom {
      font-weight: bold;
      font-size: 1.5rem;
      color: white !important;
    }
    
    .nav-link {
      color: rgba(255, 255, 255, 0.9) !important;
      transition: color 0.3s ease;
    }
    
    .nav-link:hover,
    .nav-link.active {
      color: white !important;
    }
    
    .dropdown-menu {
      border: none;
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }
    
    .dropdown-item {
      transition: background-color 0.3s ease;
    }
    
    .dropdown-item:hover {
      background-color: #f8f9fa;
    }
    
    @media (max-width: 991px) {
      .navbar-nav {
        text-align: center;
      }
      
      .dropdown-menu {
        position: static !important;
        float: none;
        width: auto;
        margin-top: 0;
        background-color: transparent;
        border: 0;
        box-shadow: none;
      }
      
      .dropdown-item {
        color: rgba(255, 255, 255, 0.9) !important;
      }
      
      .dropdown-item:hover {
        background-color: rgba(255, 255, 255, 0.1);
        color: white !important;
      }
    }
  `]
})
export class NavbarComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  currentUser: User | null = null;
  private subscriptions: Subscription[] = [];

  constructor(
    public authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Subscribe to authentication status
    this.subscriptions.push(
      this.authService.isAuthenticated$.subscribe(
        isAuth => this.isAuthenticated = isAuth
      )
    );

    // Subscribe to current user
    this.subscriptions.push(
      this.authService.currentUser$.subscribe(
        user => this.currentUser = user
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  logout(event: Event): void {
    event.preventDefault();
    
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Logout error:', error);
        // Even if logout fails on server, clear local session
        this.router.navigate(['/']);
      }
    });
  }
}
