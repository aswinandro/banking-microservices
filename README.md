# Banking Microservices Architecture

A complete banking microservices system built with Spring Boot, Spring Cloud, and in-memory data storage.

## Services
- **Eureka Server** (8761): Service discovery
- **API Gateway** (8080): Routing and authentication
- **User Service** (8081): User management
- **Account Service** (8082): Account management
- **Transaction Service** (8083): Transaction processing
- **Notification Service** (8084): Notifications

## Running Locally

### Prerequisites
- Java 17+
- Maven 3.6+

### Start Services
1. Start Eureka Server: `cd eureka-server && mvn spring-boot:run`
2. Start API Gateway: `cd api-gateway && mvn spring-boot:run`
3. Start User Service: `cd user-service && mvn spring-boot:run`
4. Start Account Service: `cd account-service && mvn spring-boot:run`
5. Start Transaction Service: `cd transaction-service && mvn spring-boot:run`
6. Start Notification Service: `cd notification-service && mvn spring-boot:run`

### API Endpoints
- Users: `GET/POST http://localhost:8080/api/users`
- Accounts: `GET/POST http://localhost:8080/api/accounts`
- Transactions: `GET/POST http://localhost:8080/api/transactions`
- Notifications: `GET http://localhost:8080/api/notifications`

### Sample Data
All services come pre-loaded with dummy data for testing.