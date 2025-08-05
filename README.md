# Online Grocery Ordering System

A comprehensive full-stack web application for online grocery ordering built with Spring Boot, Angular, and MySQL.

## 🚀 Project Overview

This is a complete online grocery ordering system that provides functionality for both customers and administrators. The system includes user authentication, product management, order processing, and comprehensive security features with both REST API and JSP-based interfaces.

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.x, Spring Security, Spring Data JPA
- **Frontend**: Angular 17+, Bootstrap 5, TypeScript
- **Database**: MySQL 8.0
- **Server-side Rendering**: JSP with JSTL
- **Build Tools**: Maven (Backend), npm/Angular CLI (Frontend)
- **Security**: JWT Authentication, SQL Injection Prevention

## ✨ Features

### Customer Features
- User Registration and Login
- Product Search and Browse by Name/Category
- Shopping Cart Management
- Order Placement and Tracking
- Order History and Status Updates
- Profile Management
- Responsive Design

### Admin Features
- Admin Dashboard with Statistics
- Customer Management (View, Search, Update)
- Product Management (CRUD Operations)
- Order Management and Status Updates
- Customer Search by Name
- Product Search and Inventory Management
- Comprehensive Admin Panel

### Security Features
- JWT-based Authentication
- Role-based Authorization (Customer/Admin)
- SQL Injection Prevention
- Input Validation and Sanitization
- Secure Password Hashing (BCrypt)
- CORS Configuration
- Authentication Guards

## 📋 Prerequisites

- **Java 17** or higher
- **Node.js 18+** and npm
- **MySQL 8.0**
- **Maven 3.6+**
- **Angular CLI** (optional, for development)

## 🚀 Quick Start

### Option 1: Automated Setup (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/chirag127/Online-Grocery-Ordering-System-6.git
   cd Online-Grocery-Ordering-System-6
   ```

2. **Run the automated setup script**
   ```bash
   chmod +x start-application.sh
   ./start-application.sh
   ```
   
   This script will:
   - Create and initialize the MySQL database
   - Build and start the Spring Boot backend
   - Install dependencies and start the Angular frontend
   - Display access URLs and credentials

### Option 2: Manual Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/chirag127/Online-Grocery-Ordering-System-6.git
   cd Online-Grocery-Ordering-System-6
   ```

2. **Setup Database**
   ```bash
   # Create MySQL database
   mysql -u root -p
   CREATE DATABASE grocery_ordering_system;
   exit
   
   # Initialize schema and data
   mysql -u root -p grocery_ordering_system < database/schema.sql
   mysql -u root -p grocery_ordering_system < database/init-database.sql
   ```

3. **Configure Backend**
   ```bash
   cd backend
   # Update src/main/resources/application.properties with your database credentials
   mvn clean install
   mvn spring-boot:run
   ```

4. **Setup Frontend** (in a new terminal)
   ```bash
   cd frontend
   npm install
   npm start
   ```

## 🌐 Access Points

- **Angular Frontend**: http://localhost:4200
- **Spring Boot Backend API**: http://localhost:8080
- **JSP Pages**: http://localhost:8080/jsp/
- **Admin Menu**: http://localhost:8080/jsp/menu

## 🔐 Default Credentials

### Admin Access
- **Username**: admin
- **Password**: admin123

### Sample Customer (for testing)
- **Email**: john.doe@email.com
- **Password**: password

## 📁 Project Structure

```
Online-Grocery-Ordering-System-6/
├── backend/                          # Spring Boot Backend
│   ├── src/main/java/com/grocery/ordering/
│   │   ├── config/                   # Security & Configuration
│   │   ├── controller/               # REST Controllers
│   │   ├── dto/                      # Data Transfer Objects
│   │   ├── entity/                   # JPA Entities
│   │   ├── repository/               # Data Repositories
│   │   ├── security/                 # JWT & Security
│   │   ├── service/                  # Business Logic
│   │   └── util/                     # Utilities & Validation
│   ├── src/main/resources/           # Configuration files
│   └── src/main/webapp/WEB-INF/jsp/  # JSP pages
├── frontend/                         # Angular Frontend
│   ├── src/app/
│   │   ├── components/               # Angular Components
│   │   ├── services/                 # HTTP Services
│   │   ├── models/                   # TypeScript Models
│   │   └── guards/                   # Route Guards
│   └── src/assets/                   # Static Assets
├── database/                         # Database Scripts
│   ├── schema.sql                    # Database Schema
│   └── init-database.sql             # Sample Data
├── docs/                             # Documentation
├── deliverables/                     # Final Submission Files
└── start-application.sh              # Automated Setup Script
```

## 🔧 API Documentation

### Authentication Endpoints
- `POST /api/auth/login` - User login
- `POST /api/auth/admin/login` - Admin login
- `POST /api/auth/register` - Customer registration
- `POST /api/auth/logout` - User logout

### Customer Endpoints
- `GET /api/customers/profile` - Get customer profile
- `PUT /api/customers/profile` - Update customer profile
- `GET /api/customers/orders` - Get customer orders
- `POST /api/customers/orders` - Create new order

### Product Endpoints
- `GET /api/products` - Get all products
- `GET /api/products/search` - Search products by name
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/in-stock` - Get in-stock products

### Admin Endpoints
- `GET /api/admin/customers` - Get all customers
- `GET /api/admin/customers/search` - Search customers
- `POST /api/admin/products` - Create product
- `PUT /api/admin/products/{id}` - Update product
- `DELETE /api/admin/products/{id}` - Delete product
- `GET /api/admin/orders` - Get all orders
- `PUT /api/admin/orders/{id}/status` - Update order status

## 🎯 JSP Pages

### Public Pages
- `/jsp/` - Home page
- `/jsp/login` - Customer login
- `/jsp/admin/login` - Admin login
- `/jsp/register` - Customer registration
- `/jsp/products/search` - Product search

### Admin Pages
- `/jsp/menu` - Admin menu
- `/jsp/admin/dashboard` - Admin dashboard
- `/jsp/admin/customers` - Customer management
- `/jsp/admin/products` - Product management
- `/jsp/admin/orders` - Order management

## 🧪 Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
npm test
```

## 🚀 Deployment

### Production Build
```bash
# Backend
cd backend
mvn clean package -DskipTests

# Frontend
cd frontend
npm run build
```

### Docker Deployment (Optional)
```bash
# Build and run with Docker Compose
docker-compose up --build
```

## 🔒 Security Features

1. **Authentication & Authorization**
   - JWT-based stateless authentication
   - Role-based access control (CUSTOMER, ADMIN)
   - Secure password hashing with BCrypt

2. **Input Validation**
   - Comprehensive input validation on all endpoints
   - SQL injection prevention
   - XSS protection
   - CSRF protection

3. **Data Protection**
   - Sensitive data encryption
   - Secure HTTP headers
   - CORS configuration

## 📊 Database Schema

### Main Tables
- `customers` - Customer information
- `admin_users` - Admin user accounts
- `products` - Product catalog
- `orders` - Customer orders
- `order_items` - Order line items

### Key Relationships
- Customer → Orders (One-to-Many)
- Order → OrderItems (One-to-Many)
- Product → OrderItems (One-to-Many)

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Ensure MySQL is running
   - Check database credentials in `application.properties`
   - Verify database exists

2. **Port Already in Use**
   - Backend: Change port in `application.properties`
   - Frontend: Use `ng serve --port 4201`

3. **CORS Issues**
   - Check CORS configuration in `SecurityConfig.java`
   - Verify frontend URL in allowed origins

## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Chirag Singhal** ([@chirag127](https://github.com/chirag127))

---
*Last Updated: August 5, 2025*
