#!/bin/bash

# Online Grocery Ordering System - Startup Script
# Author: Chirag Singhal
# Version: 1.0.0

echo "=========================================="
echo "Online Grocery Ordering System"
echo "Starting Application..."
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed. Please install Maven 3.6+."
    exit 1
fi

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "Error: Node.js is not installed. Please install Node.js 18+."
    exit 1
fi

# Check if MySQL is running
if ! command -v mysql &> /dev/null; then
    echo "Warning: MySQL client not found. Please ensure MySQL server is running."
fi

echo "Prerequisites check completed."
echo ""

# Create database if it doesn't exist
echo "Setting up database..."
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS grocery_ordering_system;"
mysql -u root -p grocery_ordering_system < database/schema.sql
mysql -u root -p grocery_ordering_system < database/init-database.sql
echo "Database setup completed."
echo ""

# Build and start backend
echo "Building and starting Spring Boot backend..."
cd backend
mvn clean install -DskipTests
echo "Backend built successfully."

# Start backend in background
echo "Starting backend server on port 8080..."
nohup mvn spring-boot:run > ../backend.log 2>&1 &
BACKEND_PID=$!
echo "Backend started with PID: $BACKEND_PID"
cd ..

# Wait for backend to start
echo "Waiting for backend to start..."
sleep 30

# Install frontend dependencies and start
echo "Installing frontend dependencies..."
cd frontend
npm install
echo "Frontend dependencies installed."

echo "Starting Angular frontend on port 4200..."
npm start &
FRONTEND_PID=$!
echo "Frontend started with PID: $FRONTEND_PID"
cd ..

echo ""
echo "=========================================="
echo "Application started successfully!"
echo "=========================================="
echo "Backend API: http://localhost:8080"
echo "Frontend: http://localhost:4200"
echo "JSP Pages: http://localhost:8080/jsp/"
echo ""
echo "Default Admin Credentials:"
echo "Username: admin"
echo "Password: admin123"
echo ""
echo "To stop the application:"
echo "kill $BACKEND_PID $FRONTEND_PID"
echo ""
echo "Logs:"
echo "Backend: tail -f backend.log"
echo "Frontend: Check terminal output"
echo "=========================================="

# Save PIDs to file for easy cleanup
echo "$BACKEND_PID" > .backend.pid
echo "$FRONTEND_PID" > .frontend.pid

# Wait for user input to stop
echo "Press Ctrl+C to stop the application..."
trap 'echo "Stopping application..."; kill $BACKEND_PID $FRONTEND_PID 2>/dev/null; rm -f .backend.pid .frontend.pid; exit' INT
wait
