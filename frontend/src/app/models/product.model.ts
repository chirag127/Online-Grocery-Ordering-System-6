/**
 * Product model interface for the grocery ordering system.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */

export interface Product {
  productId?: number;
  productName: string;
  price: number;
  quantity: number;
  description?: string;
  category?: string;
  imageUrl?: string;
  isReserved?: boolean;
  reservedBy?: number;
  createdAt?: Date;
  updatedAt?: Date;
  isActive?: boolean;
}

export interface ProductCreate {
  productName: string;
  price: number;
  quantity: number;
  description?: string;
  category?: string;
  imageUrl?: string;
}

export interface ProductUpdate {
  productName: string;
  price: number;
  quantity: number;
  description?: string;
  category?: string;
  imageUrl?: string;
}

export interface ProductSearch {
  searchTerm: string;
  category?: string;
  minPrice?: number;
  maxPrice?: number;
  inStockOnly?: boolean;
}
