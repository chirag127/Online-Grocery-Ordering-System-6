/**
 * Authentication model interfaces for the grocery ordering system.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */

export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token?: string;
  type?: string;
  id?: number;
  username?: string;
  email?: string;
  role?: string;
  message?: string;
  success: boolean;
}

export interface User {
  id: number;
  username: string;
  email: string;
  role: UserRole;
  isActive: boolean;
}

export enum UserRole {
  CUSTOMER = 'CUSTOMER',
  ADMIN = 'ADMIN',
  SUPER_ADMIN = 'SUPER_ADMIN'
}

export interface TokenPayload {
  sub: string; // username
  iat: number; // issued at
  exp: number; // expiration
  role: string;
  id: number;
}

export interface ApiResponse<T = any> {
  success: boolean;
  message: string;
  data?: T;
  timestamp: number;
}

export interface ErrorResponse {
  success: false;
  message: string;
  error?: string;
  status?: number;
  timestamp: number;
}
