# Code Documentation - Online Grocery Ordering System

**Project:** Online Grocery Ordering System  
**Version:** 1.0.0  
**Developer:** Chirag Singhal  
**Date:** August 5, 2025  

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Backend Documentation](#backend-documentation)
3. [Frontend Documentation](#frontend-documentation)
4. [Database Schema](#database-schema)
5. [API Documentation](#api-documentation)
6. [Security Implementation](#security-implementation)
7. [Configuration Guide](#configuration-guide)
8. [Deployment Guide](#deployment-guide)

## 1. Architecture Overview

### System Architecture
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Angular       │    │   Spring Boot   │    │   H2/MySQL      │
│   Frontend      │◄──►│   Backend       │◄──►│   Database      │
│   (Port 4200)   │    │   (Port 8080)   │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Technology Stack
- **Frontend:** Angular 17+, TypeScript, Bootstrap 5, RxJS
- **Backend:** Spring Boot 3.2.1, Java 21, Spring Security, JWT
- **Database:** H2 (Development), MySQL 8.0+ (Production)
- **Build Tools:** Maven (Backend), npm/Angular CLI (Frontend)
- **Testing:** JUnit 5, Mockito, Angular Testing Utilities

## 2. Backend Documentation

### 2.1 Project Structure
```
backend/
├── src/main/java/com/grocery/ordering/
│   ├── controller/          # REST Controllers
│   ├── service/            # Business Logic Services
│   ├── repository/         # Data Access Layer
│   ├── dto/               # Data Transfer Objects
│   ├── entity/            # JPA Entities
│   ├── security/          # Security Configuration
│   ├── util/              # Utility Classes
│   └── GroceryOrderingSystemApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
└── src/test/java/         # Unit Tests
```

### 2.2 Key Components

#### Controllers
- **CustomerController:** Customer management endpoints
- **ProductController:** Product CRUD operations
- **OrderController:** Order management
- **AdminController:** Admin-specific operations
- **AuthController:** Authentication endpoints

#### Services
- **CustomerService:** Customer business logic
- **ProductService:** Product management logic
- **OrderService:** Order processing logic
- **AuthService:** Authentication and authorization

#### Security
- **JwtAuthenticationEntryPoint:** JWT authentication entry point
- **AuthTokenFilter:** JWT token validation filter
- **SecurityConfig:** Spring Security configuration

### 2.3 Key Classes

#### CustomerController.java
```java
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers()
    
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO)
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id)
}
```

#### ValidationUtils.java
```java
public class ValidationUtils {
    
    public static void preventSQLInjection(String input, String fieldName)
    public static void validatePassword(String password)
    public static void validateEmail(String email)
    public static String sanitizeInput(String input)
}
```

## 3. Frontend Documentation

### 3.1 Project Structure
```
frontend/
├── src/app/
│   ├── components/
│   │   ├── home/              # Home page component
│   │   └── shared/            # Shared components
│   │       ├── header/        # Navigation header
│   │       └── footer/        # Page footer
│   ├── services/              # Angular services
│   │   ├── auth.service.ts    # Authentication service
│   │   ├── customer.service.ts # Customer operations
│   │   └── product.service.ts  # Product operations
│   ├── models/                # TypeScript interfaces
│   ├── guards/                # Route guards
│   ├── interceptors/          # HTTP interceptors
│   └── app.routes.ts          # Routing configuration
├── src/assets/                # Static assets
└── src/styles.css            # Global styles
```

### 3.2 Key Components

#### AuthService
```typescript
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private API_URL = 'http://localhost:8080/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  
  login(credentials: LoginRequest): Observable<AuthResponse>
  register(userData: RegisterRequest): Observable<AuthResponse>
  logout(): Observable<{success: boolean, message: string}>
  getCurrentUser(): Observable<User | null>
}
```

#### HomeComponent
```typescript
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  featuredProducts: Product[] = [];
  
  constructor(private productService: ProductService) {}
  
  ngOnInit(): void {
    this.loadFeaturedProducts();
  }
}
```

### 3.3 Routing Configuration
```typescript
export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./components/home/home.component')
      .then(m => m.HomeComponent)
  },
  {
    path: '**',
    redirectTo: ''
  }
];
```

## 4. Database Schema

### 4.1 Entity Relationship Diagram
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  customers  │    │   orders    │    │ order_items │
│─────────────│    │─────────────│    │─────────────│
│ customer_id │◄──┐│ order_id    │◄──┐│ order_item_id│
│ customer_name│   ││ customer_id │   ││ order_id    │
│ email       │   ││ total_amount│   ││ product_id  │
│ password    │   ││ order_status│   ││ quantity    │
│ address     │   ││ order_date  │   ││ unit_price  │
│ contact_no  │   │└─────────────┘   ││ total_price │
└─────────────┘   │                  │└─────────────┘
                  │                  │
                  │ ┌─────────────┐  │
                  │ │  products   │  │
                  │ │─────────────│  │
                  │ │ product_id  │◄─┘
                  │ │ product_name│
                  │ │ price       │
                  │ │ quantity    │
                  │ │ description │
                  │ │ category    │
                  │ └─────────────┘
                  │
                  │ ┌─────────────┐
                  │ │ admin_users │
                  │ │─────────────│
                  │ │ id          │
                  │ │ username    │
                  │ │ password    │
                  │ │ email       │
                  │ │ full_name   │
                  │ │ role        │
                  │ └─────────────┘
```

### 4.2 Table Definitions

#### customers
```sql
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    contact_number VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);
```

#### products
```sql
CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    quantity INT NOT NULL CHECK (quantity >= 0),
    description TEXT,
    category VARCHAR(50),
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    is_reserved BOOLEAN DEFAULT FALSE,
    reserved_by BIGINT
);
```

## 5. API Documentation

### 5.1 Authentication Endpoints

#### POST /api/auth/login
**Description:** Authenticate user and return JWT token  
**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "user@example.com",
    "role": "CUSTOMER"
  }
}
```

#### POST /api/auth/register
**Description:** Register new customer account  
**Request Body:**
```json
{
  "customerName": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "address": "123 Main St",
  "contactNumber": "9876543210"
}
```

### 5.2 Product Endpoints

#### GET /api/products
**Description:** Get all products  
**Response:**
```json
[
  {
    "productId": 1,
    "productName": "Red Apples",
    "price": 150.00,
    "quantity": 100,
    "description": "Fresh red apples",
    "category": "Fruits",
    "isActive": true
  }
]
```

#### GET /api/products/{id}
**Description:** Get product by ID  
**Parameters:** `id` (path) - Product ID  

#### POST /api/products (Admin only)
**Description:** Create new product  
**Headers:** `Authorization: Bearer {token}`  

### 5.3 Customer Endpoints

#### GET /api/customers (Admin only)
**Description:** Get all customers  
**Headers:** `Authorization: Bearer {token}`  

#### GET /api/customers/{id}
**Description:** Get customer by ID  
**Headers:** `Authorization: Bearer {token}`  

### 5.4 Order Endpoints

#### POST /api/orders
**Description:** Create new order  
**Headers:** `Authorization: Bearer {token}`  
**Request Body:**
```json
{
  "customerId": 1,
  "deliveryAddress": "123 Main St",
  "contactNumber": "9876543210",
  "items": [
    {
      "productId": 1,
      "quantity": 2,
      "unitPrice": 150.00
    }
  ]
}
```

## 6. Security Implementation

### 6.1 Authentication Flow
1. User submits credentials to `/api/auth/login`
2. Server validates credentials against database
3. If valid, server generates JWT token with user claims
4. Client stores token and includes in subsequent requests
5. Server validates token on protected endpoints

### 6.2 JWT Token Structure
```json
{
  "sub": "user@example.com",
  "role": "CUSTOMER",
  "iat": 1691234567,
  "exp": 1691320967
}
```

### 6.3 SQL Injection Prevention
- **Parameterized Queries:** All database queries use JPA/Hibernate
- **Input Validation:** Comprehensive validation using `ValidationUtils`
- **Sanitization:** Input sanitization for all user inputs

### 6.4 Password Security
- **Hashing:** BCrypt with strength 10
- **Validation:** Minimum 8 characters, complexity requirements
- **Storage:** Never store plain text passwords

## 7. Configuration Guide

### 7.1 Backend Configuration (application.properties)
```properties
# Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

# JWT Configuration
app.jwtSecret=mySecretKey
app.jwtExpirationMs=86400000

# Server Configuration
server.port=8080
```

### 7.2 Frontend Configuration
```typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## 8. Deployment Guide

### 8.1 Development Setup
```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend
cd frontend
npm install
ng serve
```

### 8.2 Production Deployment
```bash
# Backend
mvn clean package
java -jar target/ordering-system-1.0.0.jar

# Frontend
ng build --prod
# Deploy dist/ folder to web server
```

### 8.3 Docker Deployment
```dockerfile
# Backend Dockerfile
FROM openjdk:21-jdk-slim
COPY target/ordering-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

**Author:** Chirag Singhal  
**Last Updated:** August 5, 2025  
**Version:** 1.0.0
