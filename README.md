# Spring Boot E-commerce Backend

This project is a **full-featured backend for an e-commerce application** built with Spring Boot. It includes user authentication, product management, a shopping cart system, checkout flow, payment integration with Stripe, and role-based access control. This project mirrors a real-world backend, demonstrating professional backend development practices.

## Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL
- Optional: Postman for API testing

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/hosnaml/ecommerce-rest-api.git
   cd ecommerce-rest-api
   ```

2. Configure database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/store_api?createDatabaseIfNotExist=true
   spring.datasource.username=<your-sql-username>
   spring.datasource.password=<your-sql-password>
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints Overview

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### Cart

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart/{userId}` | Retrieve user cart |
| POST | `/api/cart/{userId}/items` | Add product to cart |
| PUT | `/api/cart/{userId}/items/{itemId}` | Update item quantity |
| DELETE | `/api/cart/{userId}/items/{itemId}` | Remove item |
| DELETE | `/api/cart/{userId}/clear` | Clear cart |
| GET | `/api/cart/{userId}/total` | Get cart total |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders/checkout` | Checkout current cart |
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get single order |

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Create new user |
| POST | `/api/auth/login` | Authenticate and get JWT |
| POST | `/api/auth/refresh` | Refresh JWT |
