/**
 * Order model interfaces for the grocery ordering system.
 * 
 * @author Chirag Singhal
 * @version 1.0.0
 */

export interface Order {
  orderId?: number;
  customerId: number;
  customerName?: string;
  customerEmail?: string;
  orderDate?: Date;
  totalAmount: number;
  orderStatus?: OrderStatus;
  deliveryAddress: string;
  contactNumber: string;
  orderItems?: OrderItem[];
}

export interface OrderItem {
  orderItemId?: number;
  orderId?: number;
  productId: number;
  productName?: string;
  quantity: number;
  unitPrice: number;
  totalPrice: number;
}

export interface OrderCreate {
  customerId: number;
  totalAmount: number;
  deliveryAddress: string;
  contactNumber: string;
  orderItems: OrderItemCreate[];
}

export interface OrderItemCreate {
  productId: number;
  quantity: number;
  unitPrice: number;
}

export interface CartItem {
  product: any; // Product interface
  quantity: number;
  totalPrice: number;
}

export enum OrderStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  PROCESSING = 'PROCESSING',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface OrderStatusUpdate {
  orderId: number;
  status: OrderStatus;
}
