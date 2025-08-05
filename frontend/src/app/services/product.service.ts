import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Product, ProductCreate, ProductUpdate, ProductSearch } from '../models/product.model';
import { AuthService } from './auth.service';

/**
 * Product service for handling product-related operations.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly API_URL = 'http://localhost:8080/api/products';
  private readonly ADMIN_API_URL = 'http://localhost:8080/api/admin/products';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  /**
   * Get all products.
   */
  getAllProducts(): Observable<Product[]> {
    return this.http.get<any>(`${this.API_URL}`, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.products || response),
      catchError(this.handleError)
    );
  }

  /**
   * Get product by ID.
   */
  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.API_URL}/${id}`, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Search products by name.
   */
  searchProductsByName(productName: string): Observable<Product[]> {
    return this.http.get<any>(`${this.API_URL}/search`, {
      params: { productName },
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.products || response),
      catchError(this.handleError)
    );
  }

  /**
   * Search products by name or category.
   */
  searchProducts(searchTerm: string): Observable<Product[]> {
    return this.http.get<any>(`${this.API_URL}/search/all`, {
      params: { searchTerm },
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.products || response),
      catchError(this.handleError)
    );
  }

  /**
   * Get products by category.
   */
  getProductsByCategory(category: string): Observable<Product[]> {
    return this.http.get<any>(`${this.API_URL}/category/${category}`, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.products || response),
      catchError(this.handleError)
    );
  }

  /**
   * Get products in stock.
   */
  getInStockProducts(): Observable<Product[]> {
    return this.http.get<any>(`${this.API_URL}/in-stock`, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.products || response),
      catchError(this.handleError)
    );
  }

  /**
   * Get all categories.
   */
  getAllCategories(): Observable<string[]> {
    return this.http.get<any>(`${this.API_URL}/categories`, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.categories || response),
      catchError(this.handleError)
    );
  }

  // Admin-only methods

  /**
   * Create new product (Admin only).
   */
  createProduct(product: ProductCreate): Observable<Product> {
    return this.http.post<any>(`${this.ADMIN_API_URL}`, product, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.product || response),
      catchError(this.handleError)
    );
  }

  /**
   * Update product (Admin only).
   */
  updateProduct(id: number, product: ProductUpdate): Observable<Product> {
    return this.http.put<any>(`${this.ADMIN_API_URL}/${id}`, product, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      map(response => response.product || response),
      catchError(this.handleError)
    );
  }

  /**
   * Delete product (Admin only).
   */
  deleteProduct(id: number): Observable<any> {
    return this.http.delete<any>(`${this.ADMIN_API_URL}/${id}`, {
      headers: this.authService.getAuthHeaders()
    }).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Update product quantity (Admin only).
   */
  updateProductQuantity(id: number, quantity: number): Observable<any> {
    return this.http.put<any>(`${this.ADMIN_API_URL}/${id}/quantity`, 
      { quantity }, 
      { headers: this.authService.getAuthHeaders() }
    ).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Handle HTTP errors.
   */
  private handleError(error: any): Observable<never> {
    let errorMessage = 'An error occurred';
    
    if (error.error?.message) {
      errorMessage = error.error.message;
    } else if (error.message) {
      errorMessage = error.message;
    }

    return throwError(() => new Error(errorMessage));
  }
}
