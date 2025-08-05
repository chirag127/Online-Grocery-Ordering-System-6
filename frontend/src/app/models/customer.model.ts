/**
 * Customer model interface for the grocery ordering system.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */

export interface Customer {
  customerId?: number;
  customerName: string;
  email: string;
  address: string;
  contactNumber: string;
  createdAt?: Date;
  updatedAt?: Date;
  isActive?: boolean;
}

export interface CustomerRegistration {
  customerName: string;
  email: string;
  password: string;
  confirmPassword: string;
  address: string;
  contactNumber: string;
}

export interface CustomerUpdate {
  customerName: string;
  email: string;
  address: string;
  contactNumber: string;
}

export interface PasswordUpdate {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}
