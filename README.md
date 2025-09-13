# 🏦 Banking Microservices Architecture - Complete Guide

A comprehensive banking system built with Spring Boot microservices architecture, featuring service discovery, API gateway, and complete banking operations.

## 📋 Table of Contents
- [Architecture Overview](#-architecture-overview)
- [Prerequisites](#-prerequisites)
- [Quick Setup](#-quick-setup)
- [Services Details](#-services-details)
- [API Testing](#-api-testing)
- [Postman Testing](#-postman-testing)
- [Troubleshooting](#-troubleshooting)
- [Production Deployment](#-production-deployment)

---

## 🏗️ Architecture Overview

### Services Architecture
```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Web Browser   │    │    Mobile App    │    │   Third Party   │
└─────────┬───────┘    └────────┬─────────┘    └─────────┬───────┘
          │                     │                        │
          └─────────────────────┼────────────────────────┘
                                │
                    ┌───────────▼────────────┐
                    │    API Gateway         │
                    │    Port: 8080          │
                    └───────────┬────────────┘
                                │
                    ┌───────────▼────────────┐
                    │   Eureka Server        │
                    │   Port: 8761           │
                    └────────────────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
┌───────▼──────┐    ┌───────────▼──────┐    ┌──────────▼──────┐
│ User Service │    │ Account Service  │    │Transaction Svc  │
│ Port: 8081   │    │ Port: 8082       │    │Port: 8083       │
└──────────────┘    └──────────────────┘    └─────────────────┘
                                │
                    ┌───────────▼────────────┐
                    │ Notification Service   │
                    │ Port: 8084             │
                    └────────────────────────┘
```

### Technology Stack
- **Framework**: Spring Boot 3.1.0
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Communication**: OpenFeign
- **Data Storage**: In-Memory (ConcurrentHashMap)
- **Build Tool**: Maven 3.6+
- **Java Version**: 17+
- **Containerization**: Docker & Docker Compose

---

## 📦 Prerequisites

### Required Software
- ☕ **Java 17+** - [Download Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
- 🔧 **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
- 🐳 **Docker Desktop** (Optional) - [Download Docker](https://www.docker.com/products/docker-desktop/)
- 🧪 **Postman** - [Download Postman](https://www.postman.com/downloads/)

### Windows Users
- **PowerShell 5.0+** (Included with Windows 10/11)
- **Git Bash** (Optional but recommended)

### Verify Installation
```bash
# Check Java version
java -version
# Expected: java 17.x.x or higher

# Check Maven version  
mvn -version
# Expected: Apache Maven 3.6.x or higher

# Check Docker version (if using containers)
docker --version
# Expected: Docker version 20.x.x or higher
```

---

## 🚀 Quick Setup

### Method 1: Automated Setup (Recommended)

#### For Windows (PowerShell):
```powershell
# 1. Create setup script
New-Item -Path "setup-banking.ps1" -ItemType File
# Copy the PowerShell script from the previous artifact

# 2. Run setup
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
.\setup-banking.ps1

# 3. Navigate to project
cd banking-microservices

# 4. Build all services
.\build-all.ps1

# 5. Start all services
.\start-all.ps1
```

#### For Linux/Mac (Bash):
```bash
# 1. Create and run setup script
chmod +x setup-banking.sh
./setup-banking.sh

# 2. Navigate to project
cd banking-microservices

# 3. Build all services
./build-all.sh

# 4. Start all services
./start-all.sh
```

### Method 2: Docker Compose
```bash
# After running setup script
cd banking-microservices

# Build images and start containers
docker-compose up --build

# Or run in background
docker-compose up -d --build
```

### Method 3: Manual Setup
```bash
# 1. Start services in separate terminals in this order:

# Terminal 1: Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2: API Gateway (wait 30 seconds)
cd api-gateway
mvn spring-boot:run

# Terminal 3: User Service (wait 15 seconds)
cd user-service
mvn spring-boot:run

# Terminal 4: Account Service (wait 10 seconds)
cd account-service
mvn spring-boot:run

# Terminal 5: Transaction Service (wait 10 seconds)
cd transaction-service
mvn spring-boot:run

# Terminal 6: Notification Service (wait 10 seconds)
cd notification-service
mvn spring-boot:run
```

---

## 🔍 Services Details

### 1. Eureka Server (Service Discovery)
- **Port**: 8761
- **Purpose**: Service registry and discovery
- **Health Check**: http://localhost:8761/actuator/health
- **Dashboard**: http://localhost:8761
- **Expected Services**: 5 registered services

**Key Features:**
- Automatic service registration
- Load balancing support
- Health monitoring
- Service instance management

### 2. API Gateway
- **Port**: 8080
- **Purpose**: Single entry point, routing, load balancing
- **Health Check**: http://localhost:8080/actuator/health
- **Routes**:
  - `/api/users/**` → User Service
  - `/api/accounts/**` → Account Service  
  - `/api/transactions/**` → Transaction Service
  - `/api/notifications/**` → Notification Service

**Key Features:**
- Request routing and filtering
- Cross-cutting concerns (logging, security)
- Rate limiting capabilities
- Circuit breaker pattern

### 3. User Service
- **Port**: 8081  
- **Purpose**: Customer management and authentication
- **Database**: In-memory with 5 pre-loaded users
- **Health Check**: http://localhost:8081/actuator/health

**Sample Data:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe", 
  "email": "john.doe@email.com",
  "phoneNumber": "+1234567890",
  "address": "123 Main St, New York, NY"
}
```

### 4. Account Service  
- **Port**: 8082
- **Purpose**: Bank account management and operations
- **Database**: In-memory with 7 pre-loaded accounts
- **Dependencies**: User Service (via Feign Client)
- **Health Check**: http://localhost:8082/actuator/health

**Sample Data:**
```json
{
  "id": 1,
  "userId": 1,
  "accountNumber": "ACC1001",
  "accountType": "CHECKING",
  "balance": 5000.00,
  "currency": "USD",
  "isActive": true
}
```

### 5. Transaction Service
- **Port**: 8083  
- **Purpose**: Financial transaction processing
- **Database**: In-memory with transaction history
- **Dependencies**: Account Service (via Feign Client)
- **Health Check**: http://localhost:8083/actuator/health

**Transaction Types:**
- DEPOSIT
- WITHDRAWAL  
- TRANSFER
- PAYMENT
- REFUND

### 6. Notification Service
- **Port**: 8084
- **Purpose**: Multi-channel notification delivery  
- **Database**: In-memory with notification history
- **Health Check**: http://localhost:8084/actuator/health

**Notification Channels:**
- EMAIL
- SMS
- PUSH
- IN_APP

---

## 🧪 API Testing

### Health Checks (Priority 1 - Test First)

```bash
# 1. Check API Gateway
curl http://localhost:8080/actuator/health
# Expected: {"status":"UP"}

# 2. Check Eureka Dashboard  
curl http://localhost:8761
# Expected: HTML page with service registry

# 3. Check Individual Services
curl http://localhost:8081/actuator/health  # User Service
curl http://localhost:8082/actuator/health  # Account Service  
curl http://localhost:8083/actuator/health  # Transaction Service
curl http://localhost:8084/actuator/health  # Notification Service
```

### User Service API Tests

```bash
# Get all users (should return 5 users)
curl -X GET http://localhost:8080/api/users

# Get specific user
curl -X GET http://localhost:8080/api/users/1

# Create new user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "test.user@email.com",
    "phoneNumber": "+1555987654",
    "address": "789 Test Street, Test City, TC"
  }'

# Update user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John Updated",
    "lastName": "Doe Updated", 
    "email": "john.updated@email.com",
    "phoneNumber": "+1234567890",
    "address": "123 Updated St, New York, NY"
  }'

# Find user by email
curl -X GET "http://localhost:8080/api/users/email/john.doe@email.com"
```

### Account Service API Tests

```bash
# Get all accounts (should return 7 accounts)  
curl -X GET http://localhost:8080/api/accounts

# Get specific account
curl -X GET http://localhost:8080/api/accounts/1

# Get accounts for specific user
curl -X GET http://localhost:8080/api/accounts/user/1

# Create new account
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "accountType": "SAVINGS",
    "balance": 1000.00
  }'

# Get account balance
curl -X GET http://localhost:8080/api/accounts/1/balance

# Credit account (add money)
curl -X POST "http://localhost:8080/api/accounts/1/credit?amount=500.00"

# Debit account (remove money) 
curl -X POST "http://localhost:8080/api/accounts/1/debit?amount=100.00"

# Find account by number
curl -X GET http://localhost:8080/api/accounts/number/ACC1001
```

### Transaction Service API Tests

```bash
# Get all transactions
curl -X GET http://localhost:8080/api/transactions

# Get specific transaction
curl -X GET http://localhost:8080/api/transactions/1

# Get transactions for account
curl -X GET http://localhost:8080/api/transactions/account/1

# Make a deposit
curl -X POST "http://localhost:8080/api/transactions/deposit?accountId=1&amount=250.00&description=Salary deposit"

# Make a withdrawal  
curl -X POST "http://localhost:8080/api/transactions/withdraw?accountId=1&amount=50.00&description=ATM withdrawal"

# Transfer between accounts
curl -X POST "http://localhost:8080/api/transactions/transfer?fromAccountId=1&toAccountId=2&amount=300.00&description=Transfer to savings"

# Get transactions by type
curl -X GET http://localhost:8080/api/transactions/type/TRANSFER

# Get transactions by status
curl -X GET http://localhost:8080/api/transactions/status/COMPLETED

# Process pending transaction
curl -X POST http://localhost:8080/api/transactions/5/process
```

### Notification Service API Tests

```bash  
# Get all notifications
curl -X GET http://localhost:8080/api/notifications

# Get notifications for user
curl -X GET http://localhost:8080/api/notifications/user/1

# Get unread notifications for user  
curl -X GET http://localhost:8080/api/notifications/user/1/unread

# Get unread count
curl -X GET http://localhost:8080/api/notifications/user/1/unread-count

# Send email notification
curl -X POST "http://localhost:8080/api/notifications/send-email?userId=1&title=Test Email&message=This is a test email notification&email=test@email.com"

# Send SMS notification
curl -X POST "http://localhost:8080/api/notifications/send-sms?userId=1&title=Test SMS&message=This is a test SMS&phoneNumber=+1234567890"

# Send push notification
curl -X POST "http://localhost:8080/api/notifications/send-push?userId=1&title=Test Push&message=This is a test push notification"

# Mark notification as read
curl -X POST http://localhost:8080/api/notifications/1/read

# Get notifications by type
curl -X GET http://localhost:8080/api/notifications/type/EMAIL

# Get notifications by priority
curl -X GET http://localhost:8080/api/notifications/priority/HIGH
```

---

## 📮 Postman Testing

### Import Postman Collection

Create a new Postman collection with this JSON:

```json
{
  "info": {
    "name": "Banking Microservices",
    "description": "Complete API testing for banking microservices"
  },
  "variable": [
    {
      "key": "base_url",
      "value": "http://localhost:8080"
    },
    {
      "key": "eureka_url", 
      "value": "http://localhost:8761"
    }
  ],
  "item": [
    {
      "name": "Health Checks",
      "item": [
        {
          "name": "API Gateway Health",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/actuator/health"
          },
          "event": [
            {
              "listen": "test",
              "script": {
                "exec": [
                  "pm.test('API Gateway is healthy', function () {",
                  "    pm.response.to.have.status(200);",
                  "    pm.expect(pm.response.json().status).to.eql('UP');",
                  "});"
                ]
              }
            }
          ]
        },
        {
          "name": "Eureka Dashboard",
          "request": {
            "method": "GET", 
            "url": "{{eureka_url}}"
          }
        }
      ]
    },
    {
      "name": "User Service",
      "item": [
        {
          "name": "Get All Users",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/users"
          },
          "event": [
            {
              "listen": "test",
              "script": {
                "exec": [
                  "pm.test('Users returned successfully', function () {",
                  "    pm.response.to.have.status(200);",
                  "    const users = pm.response.json();",
                  "    pm.expect(users).to.be.an('array');",
                  "    pm.expect(users.length).to.be.greaterThan(0);",
                  "});"
                ]
              }
            }
          ]
        },
        {
          "name": "Create User",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/users",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"firstName\": \"Postman\",\n  \"lastName\": \"Test\",\n  \"email\": \"postman.test@email.com\",\n  \"phoneNumber\": \"+1555000001\",\n  \"address\": \"123 Postman Street, Test City, TC\"\n}"
            }
          },
          "event": [
            {
              "listen": "test", 
              "script": {
                "exec": [
                  "pm.test('User created successfully', function () {",
                  "    pm.response.to.have.status(201);",
                  "    const user = pm.response.json();",
                  "    pm.expect(user.firstName).to.eql('Postman');",
                  "    pm.environment.set('created_user_id', user.id);",
                  "});"
                ]
              }
            }
          ]
        },
        {
          "name": "Get User by ID",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/users/1"
          }
        }
      ]
    },
    {
      "name": "Account Service", 
      "item": [
        {
          "name": "Get All Accounts",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/accounts"
          },
          "event": [
            {
              "listen": "test",
              "script": {
                "exec": [
                  "pm.test('Accounts returned successfully', function () {",
                  "    pm.response.to.have.status(200);",
                  "    const accounts = pm.response.json();", 
                  "    pm.expect(accounts).to.be.an('array');",
                  "    pm.expect(accounts.length).to.be.greaterThan(0);",
                  "});"
                ]
              }
            }
          ]
        },
        {
          "name": "Create Account",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/accounts",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"userId\": 1,\n  \"accountType\": \"CHECKING\",\n  \"balance\": 1000.00\n}"
            }
          }
        },
        {
          "name": "Get Account Balance",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/accounts/1/balance"
          }
        },
        {
          "name": "Credit Account",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/accounts/1/credit?amount=500.00"
          }
        }
      ]
    },
    {
      "name": "Transaction Service",
      "item": [
        {
          "name": "Get All Transactions", 
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/transactions"
          }
        },
        {
          "name": "Make Deposit",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/transactions/deposit?accountId=1&amount=250.00&description=Test deposit from Postman"
          },
          "event": [
            {
              "listen": "test",
              "script": {
                "exec": [
                  "pm.test('Deposit successful', function () {",
                  "    pm.response.to.have.status(200);",
                  "    const transaction = pm.response.json();",
                  "    pm.expect(transaction.type).to.eql('DEPOSIT');",
                  "    pm.expect(transaction.status).to.eql('COMPLETED');",
                  "});"
                ]
              }
            }
          ]
        },
        {
          "name": "Transfer Money",
          "request": {
            "method": "POST", 
            "url": "{{base_url}}/api/transactions/transfer?fromAccountId=1&toAccountId=2&amount=100.00&description=Test transfer from Postman"
          }
        },
        {
          "name": "Get Account Transactions",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/transactions/account/1"
          }
        }
      ]
    },
    {
      "name": "Notification Service",
      "item": [
        {
          "name": "Get All Notifications",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/notifications"
          }
        },
        {
          "name": "Send Email Notification",
          "request": {
            "method": "POST",
            "url": "{{base_url}}/api/notifications/send-email?userId=1&title=Postman Test&message=This is a test notification from Postman&email=postman.test@email.com"
          }
        },
        {
          "name": "Get User Notifications",
          "request": {
            "method": "GET", 
            "url": "{{base_url}}/api/notifications/user/1"
          }
        },
        {
          "name": "Get Unread Count",
          "request": {
            "method": "GET",
            "url": "{{base_url}}/api/notifications/user/1/unread-count"
          }
        }
      ]
    }
  ]
}
```

### Test Execution Order

**Phase 1: Health Checks**
1. API Gateway Health
2. Eureka Dashboard  
3. Individual service health checks

**Phase 2: Core Services**
1. Get All Users → Create User → Get User by ID
2. Get All Accounts → Create Account → Check Balance
3. Get All Transactions → Make Deposit → Transfer Money  
4. Get All Notifications → Send Email → Check Unread Count

**Phase 3: Integration Testing**
1. Create User → Create Account for User → Make Transaction → Verify Notification

### Postman Test Scripts

**Collection Level Pre-request Script:**
```javascript
// Set dynamic timestamps
pm.globals.set("timestamp", Date.now());

// Set random values  
pm.globals.set("random_email", `test${Math.floor(Math.random() * 10000)}@email.com`);
pm.globals.set("random_phone", `+155500${Math.floor(Math.random() * 10000)}`);
```

**Collection Level Test Script:**
```javascript
// Log response time
console.log(`Response time: ${pm.response.responseTime}ms`);

// Verify response structure
if (pm.response.json) {
    const response = pm.response.json();
    if (Array.isArray(response)) {
        console.log(`Returned ${response.length} items`);
    }
}
```

---

## 🔧 Troubleshooting

### Common Issues and Solutions

#### 1. Services Not Starting

**Problem**: Service fails to start with port binding error
```
Port 8080 was already in use
```

**Solution**:
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac  
lsof -ti:8080 | xargs kill -9
```

#### 2. Service Not Registering with Eureka

**Problem**: Service starts but doesn't appear in Eureka dashboard

**Check**:
- Eureka server is running on port 8761
- Service application.yml has correct eureka configuration
- Network connectivity between services

**Solution**:
```yaml
# Ensure this is in each service's application.yml
eureka:
  client:
    service-url:
      default-zone: http://localhost:8761/eureka/
  instance:
    prefer-ip-address: true
```

#### 3. Feign Client Connection Issues

**Problem**: Account Service can't connect to User Service
```
feign.RetryableException: Connection refused
```

**Solution**:
- Verify User Service is running and registered in Eureka  
- Check Feign client configuration
- Verify service names match exactly

#### 4. Maven Build Issues

**Problem**: Build fails with dependency resolution errors

**Solution**:
```bash
# Clear Maven cache
mvn dependency:purge-local-repository

# Clean and rebuild
mvn clean install -U
```

#### 5. Memory Issues

**Problem**: Java heap space errors during startup

**Solution**:
```bash
# Set JVM options
export MAVEN_OPTS="-Xmx2g -Xms512m"

# Or for specific service
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx1g"
```

### Service Startup Verification

**Check Service Health:**
```bash
#!/bin/bash
services=("8761" "8080" "8081" "8082" "8083" "8084")
service_names=("Eureka" "Gateway" "User" "Account" "Transaction" "Notification")

for i in "${!services[@]}"; do
    port=${services[$i]}
    name=${service_names[$i]}
    
    if curl -s -f "http://localhost:$port/actuator/health" > /dev/null; then
        echo "✅ $name Service ($port) - UP"
    else
        echo "❌ $name Service ($port) - DOWN"
    fi
done
```

### Log Analysis

**Check Application Logs:**
```bash
# View logs (if using start scripts)
tail -f logs/user-service.log
tail -f logs/account-service.log  
tail -f logs/transaction-service.log
tail -f logs/notification-service.log
```

**Common Log Patterns to Monitor:**
- Service registration: `Registered instance USER-SERVICE`
- Health check: `Exposing 2 endpoint(s) beneath base path '/actuator'`
- Database initialization: `initialized with X records`
- Feign client calls: `Creating feign client for USER-SERVICE`

### Performance Monitoring

**Memory Usage:**
```bash
# Check Java processes
jps -l
jstat -gc <PID>

# System resources
top -p <PID>
```

**Connection Monitoring:**
```bash  
# Check port connections
netstat -tlnp | grep java

# Monitor HTTP requests
curl -w "@curl-format.txt" -o /dev/null -s "http://localhost:8080/api/users"
```

---

## 🚀 Production Deployment

### Docker Deployment

**Build and Deploy:**
```bash
# Build all services
./build-all.sh

# Create Docker images
docker-compose build

# Start in production mode
docker-compose up -d

# Scale services
docker-compose up -d --scale user-service=2 --scale account-service=2
```

### Kubernetes Deployment

**Sample Kubernetes Manifest:**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
      - name: user-service
        image: banking/user-service:latest
        ports:
        - containerPort: 8081
        env:
        - name: EUREKA_SERVER_URL
          value: "http://eureka-server:8761/eureka"
---
apiVersion: v1
kind: Service  
metadata:
  name: user-service
spec:
  selector:
    app: user-service
  ports:
  - port: 8081
    targetPort: 8081
  type: LoadBalancer
```

### Environment-Specific Configuration

**Production application.yml:**
```yaml  
server:
  port: 8081

spring:
  application:
    name: user-service
  profiles:
    active: production

eureka:
  client:
    service-url:
      default-zone: ${EUREKA_SERVER_URL:http://eureka-server:8761/eureka}
  instance:
    prefer-ip-address: true
    hostname: ${HOSTNAME:localhost}

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always

logging:
  level:
    com.banking: INFO
    org.springframework.cloud: DEBUG
  pattern:
    file: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  file:
    name: /var/log/user-service.log
```

### Monitoring and Observability

**Health Check Endpoints:**
- Eureka: http://eureka-server:8761/actuator/health
- Gateway: http://api-gateway:8080/actuator/health  
- Services: http://service-name:port/actuator/health

**Metrics Collection:**
```yaml
# Add to each service
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

### Security Considerations

**Production Security Checklist:**
- [ ] Enable Spring Security
- [ ] Implement JWT authentication  
- [ ] Use HTTPS/TLS encryption
- [ ] Configure CORS policies
- [ ] Add rate limiting
- [ ] Implement audit logging
- [ ] Use secrets management
- [ ] Enable security headers
- [ ] Validate all inputs
- [ ] Configure firewall rules

**Sample Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/api/users/**").hasRole("USER")
                .requestMatchers("/api/accounts/**").hasRole("ACCOUNT_HOLDER") 
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .build();
    }
}
```

---

## 📊 Performance Benchmarks

### Expected Response Times (Local Development)
- Health checks: < 50ms
- User operations: < 100ms  
- Account operations: < 150ms
- Transaction processing: < 200ms
- Notification sending: < 100ms

### Load Testing with Apache Bench
```bash
# Test user endpoint
ab -n 1000 -c 10 http://localhost:8080/api/users

# Test account balance
ab -n 500 -c 5 http://localhost:8080/api/accounts/1/balance

# Test transaction creation  
ab -n 100 -c 2 -p post-data.json -T 'application/json' \
   http://localhost:8080/api/transactions/deposit
```

### Scalability Recommendations
- **Development**: Single instance of each service
- **Staging**: 2 instances of business services  
- **Production**: 3+ instances with load balancing

## 📈 Monitoring and Observability

### Application Metrics

**Key Performance Indicators (KPIs):**
```
Service Health:
- Service uptime: > 99.9%
- Response time: < 200ms (95th percentile)
- Error rate: < 0.1%

Business Metrics:
- User registrations per hour
- Account creations per day  
- Transaction volume and value
- Notification delivery rate
```

**Prometheus Metrics Configuration:**
```yaml
# Add to application.yml for each service
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
    tags:
      application: ${spring.application.name}
      environment: ${spring.profiles.active:default}
```

### Logging Strategy

**Centralized Logging with ELK Stack:**
```yaml
# logback-spring.xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp/>
                <logLevel/>
                <loggerName/>
                <message/>
                <mdc/>
                <pattern>
                    <pattern>
                        {
                            "service": "${spring.application.name:-}",
                            "trace": "%X{traceId:-}",
                            "span": "%X{spanId:-}"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

### Distributed Tracing

**Zipkin Integration:**
```yaml
# Add to pom.xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>

# Add to application.yml
spring:
  zipkin:
    base-url: http://zipkin-server:9411
  sleuth:
    sampler:
      probability: 0.1
```

---

## 🔐 Security Implementation

### JWT Authentication Setup

**Security Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-resource-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jose</artifactId>
</dependency>
```

**JWT Security Configuration:**
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasRole("USER")
                .requestMatchers("/api/accounts/**").hasRole("ACCOUNT_HOLDER")
                .requestMatchers("/api/transactions/**").hasRole("ACCOUNT_HOLDER")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
            )
            .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = 
            new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authConverter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://auth-server:8090/.well-known/jwks.json").build();
    }
}
```

### API Rate Limiting

**Redis-based Rate Limiting:**
```java
@Component
@Slf4j
public class RateLimitingFilter implements Filter {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientId = getClientId(httpRequest);
        String key = "rate_limit:" + clientId;
        
        String currentRequests = redisTemplate.opsForValue().get(key);
        if (currentRequests == null) {
            redisTemplate.opsForValue().set(key, "1", Duration.ofMinutes(1));
        } else if (Integer.parseInt(currentRequests) >= 100) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Rate limit exceeded");
            return;
        } else {
            redisTemplate.opsForValue().increment(key);
        }
        
        chain.doFilter(request, response);
    }
    
    private String getClientId(HttpServletRequest request) {
        // Extract from JWT or use IP address
        return request.getRemoteAddr();
    }
}
```

---

## 🚀 Advanced Features

### Circuit Breaker Pattern

**Resilience4j Configuration:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
```

```java
@Component
public class AccountServiceClient {
    
    @CircuitBreaker(name = "account-service", fallbackMethod = "getAccountFallback")
    @TimeLimiter(name = "account-service")
    @Retry(name = "account-service")
    public CompletableFuture<Account> getAccountAsync(Long accountId) {
        return CompletableFuture.supplyAsync(() -> 
            accountServiceFeign.getAccountById(accountId));
    }
    
    public CompletableFuture<Account> getAccountFallback(Long accountId, Exception ex) {
        log.warn("Fallback for account {}: {}", accountId, ex.getMessage());
        Account fallbackAccount = new Account();
        fallbackAccount.setId(accountId);
        fallbackAccount.setBalance(BigDecimal.ZERO);
        return CompletableFuture.completedFuture(fallbackAccount);
    }
}
```

**Circuit Breaker Configuration:**
```yaml
resilience4j:
  circuitbreaker:
    instances:
      account-service:
        sliding-window-size: 10
        minimum-number-of-calls: 5
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s
        permitted-number-of-calls-in-half-open-state: 3
  timelimiter:
    instances:
      account-service:
        timeout-duration: 2s
  retry:
    instances:
      account-service:
        max-attempts: 3
        wait-duration: 500ms
```

### Caching Strategy

**Redis Caching Configuration:**
```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }
}

@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @CacheEvict(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
```

### Event-Driven Architecture

**Spring Cloud Stream Configuration:**
```yaml
spring:
  cloud:
    stream:
      bindings:
        transaction-events:
          destination: transaction.events
          content-type: application/json
        notification-events:
          destination: notification.events
          content-type: application/json
```

```java
@Component
@Slf4j
public class TransactionEventPublisher {
    
    @Autowired
    private StreamBridge streamBridge;
    
    public void publishTransactionEvent(Transaction transaction) {
        TransactionEvent event = new TransactionEvent(
            transaction.getId(),
            transaction.getType(),
            transaction.getAmount(),
            transaction.getFromAccountId(),
            transaction.getToAccountId()
        );
        
        streamBridge.send("transaction-events", event);
        log.info("Published transaction event: {}", event);
    }
}

@Component
@Slf4j
public class NotificationEventListener {
    
    @StreamListener("notification-events")
    public void handleTransactionEvent(TransactionEvent event) {
        // Generate notification based on transaction event
        notificationService.sendTransactionNotification(event);
        log.info("Processed transaction event: {}", event);
    }
}
```

---

## 📊 Data Management

### Database Migration (H2 to PostgreSQL)

**PostgreSQL Configuration:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/banking_db
    username: ${DB_USERNAME:banking_user}
    password: ${DB_PASSWORD:banking_pass}
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
  
  flyway:
    locations: classpath:db/migration
    baseline-on-migrate: true
```

**Sample Migration Script (V1__Create_users_table.sql):**
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_created_at ON users(created_at);
```

### Data Backup and Recovery

**PostgreSQL Backup Strategy:**
```bash
#!/bin/bash
# backup-database.sh

DB_NAME="banking_db"
BACKUP_DIR="/var/backups/banking"
DATE=$(date +"%Y%m%d_%H%M%S")

# Create backup directory
mkdir -p $BACKUP_DIR

# Full database backup
pg_dump -h localhost -U banking_user -d $DB_NAME > $BACKUP_DIR/banking_db_$DATE.sql

# Compress backup
gzip $BACKUP_DIR/banking_db_$DATE.sql

# Keep only last 7 days of backups
find $BACKUP_DIR -name "*.gz" -mtime +7 -delete

echo "Backup completed: banking_db_$DATE.sql.gz"
```

### Data Validation and Integrity

**Advanced Validation:**
```java
@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Min(1)
    private Long userId;
    
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^ACC[0-9]+$")
    private String accountNumber;
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal balance;
    
    @PrePersist
    @PreUpdate
    private void validate() {
        if (balance.compareTo(BigDecimal.ZERO) < 0 && !accountType.equals("CREDIT")) {
            throw new IllegalStateException("Non-credit accounts cannot have negative balance");
        }
    }
}
```

---

## 🎯 Testing Strategy

### Unit Testing

**Service Layer Tests:**
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        
        // When
        User created = userService.createUser(user);
        
        // Then
        assertThat(created.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).save(user);
    }
    
    @Test
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");
        
        User newUser = new User();
        newUser.setEmail("existing@example.com");
        
        when(userRepository.findByEmail("existing@example.com"))
            .thenReturn(Optional.of(existingUser));
        
        // When & Then
        assertThatThrownBy(() -> userService.createUser(newUser))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("already exists");
    }
}
```

### Integration Testing

**Web Layer Integration Tests:**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void shouldCreateAndRetrieveUser() {
        // Given
        User user = new User();
        user.setFirstName("Integration");
        user.setLastName("Test");
        user.setEmail("integration@test.com");
        user.setPhoneNumber("+1234567890");
        
        // When - Create user
        ResponseEntity<User> createResponse = restTemplate.postForEntity(
            "/api/users", user, User.class);
        
        // Then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        User createdUser = createResponse.getBody();
        assertThat(createdUser.getId()).isNotNull();
        
        // When - Retrieve user
        ResponseEntity<User> getResponse = restTemplate.getForEntity(
            "/api/users/" + createdUser.getId(), User.class);
        
        // Then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getEmail()).isEqualTo("integration@test.com");
    }
}
```

### Contract Testing

**Spring Cloud Contract:**
```groovy
// user-service/src/test/resources/contracts/get_user.groovy
Contract.make {
    description "should return user by id"
    
    request {
        method GET()
        url "/api/users/1"
    }
    
    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
            id: 1,
            firstName: "John",
            lastName: "Doe",
            email: "john.doe@email.com"
        ])
    }
}
```

### Load Testing

**JMeter Test Plan:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2">
  <hashTree>
    <TestPlan>
      <stringProp name="TestPlan.comments">Banking API Load Test</stringProp>
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
    </TestPlan>
    <hashTree>
      <ThreadGroup>
        <stringProp name="ThreadGroup.num_threads">50</stringProp>
        <stringProp name="ThreadGroup.ramp_time">10</stringProp>
        <stringProp name="ThreadGroup.duration">300</stringProp>
      </ThreadGroup>
      <hashTree>
        <HTTPSamplerProxy>
          <stringProp name="HTTPSampler.domain">localhost</stringProp>
          <stringProp name="HTTPSampler.port">8080</stringProp>
          <stringProp name="HTTPSampler.path">/api/users</stringProp>
          <stringProp name="HTTPSampler.method">GET</stringProp>
        </HTTPSamplerProxy>
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>
```

---

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

**.github/workflows/ci-cd.yml:**
```yaml
name: Banking Microservices CI/CD

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    services:
      postgres:
        image: postgres:13
        env:
          POSTGRES_PASSWORD: postgres
          POSTGRES_DB: banking_test
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5

    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Cache Maven packages
      uses: actions/cache@v3
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
        
    - name: Run tests
      run: |
        services=("eureka-server" "user-service" "account-service" "transaction-service" "notification-service")
        for service in "${services[@]}"; do
          cd $service
          mvn clean test
          cd ..
        done
        
    - name: Generate test report
      uses: dorny/test-reporter@v1
      if: success() || failure()
      with:
        name: Maven Tests
        path: '**/target/surefire-reports/*.xml'
        reporter: java-junit

  build-and-deploy:
    needs: test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Build services
      run: |
        services=("eureka-server" "api-gateway" "user-service" "account-service" "transaction-service" "notification-service")
        for service in "${services[@]}"; do
          cd $service
          mvn clean package -DskipTests
          cd ..
        done
        
    - name: Build Docker images
      run: |
        services=("eureka-server" "api-gateway" "user-service" "account-service" "transaction-service" "notification-service")
        for service in "${services[@]}"; do
          docker build -t banking/$service:${{ github.sha }} $service/
          docker tag banking/$service:${{ github.sha }} banking/$service:latest
        done
        
    - name: Deploy to staging
      run: |
        echo "Deploying to staging environment..."
        # Add deployment commands here
```

### Jenkins Pipeline

**Jenkinsfile:**
```groovy
pipeline {
    agent any
    
    tools {
        maven 'Maven-3.8.1'
        jdk 'JDK-17'
    }
    
    environment {
        DOCKER_REGISTRY = 'your-registry.com'
        DOCKER_REPO = 'banking'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Test') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        script {
                            def services = ['eureka-server', 'user-service', 'account-service', 'transaction-service', 'notification-service']
                            services.each { service ->
                                dir(service) {
                                    sh 'mvn clean test'
                                }
                            }
                        }
                    }
                    post {
                        always {
                            publishTestResults testResultsPattern: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                
                stage('Integration Tests') {
                    steps {
                        sh 'docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit'
                    }
                    post {
                        always {
                            sh 'docker-compose -f docker-compose.test.yml down'
                        }
                    }
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    def services = ['eureka-server', 'api-gateway', 'user-service', 'account-service', 'transaction-service', 'notification-service']
                    services.each { service ->
                        dir(service) {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }
        
        stage('Docker Build & Push') {
            steps {
                script {
                    def services = ['eureka-server', 'api-gateway', 'user-service', 'account-service', 'transaction-service', 'notification-service']
                    services.each { service ->
                        def image = docker.build("${DOCKER_REGISTRY}/${DOCKER_REPO}/${service}:${BUILD_NUMBER}", service)
                        docker.withRegistry("https://${DOCKER_REGISTRY}", 'docker-registry-credentials') {
                            image.push()
                            image.push('latest')
                        }
                    }
                }
            }
        }
        
        stage('Deploy to Staging') {
            steps {
                script {
                    // Deploy to staging environment
                    sh """
                        kubectl set image deployment/user-service user-service=${DOCKER_REGISTRY}/${DOCKER_REPO}/user-service:${BUILD_NUMBER}
                        kubectl set image deployment/account-service account-service=${DOCKER_REGISTRY}/${DOCKER_REPO}/account-service:${BUILD_NUMBER}
                        kubectl set image deployment/transaction-service transaction-service=${DOCKER_REGISTRY}/${DOCKER_REPO}/transaction-service:${BUILD_NUMBER}
                        kubectl set image deployment/notification-service notification-service=${DOCKER_REGISTRY}/${DOCKER_REPO}/notification-service:${BUILD_NUMBER}
                    """
                }
            }
        }
        
        stage('Smoke Tests') {
            steps {
                script {
                    // Wait for deployments to be ready
                    sh 'kubectl rollout status deployment/user-service'
                    sh 'kubectl rollout status deployment/account-service'
                    
                    // Run smoke tests
                    sh 'curl -f http://staging-api-gateway/actuator/health || exit 1'
                    sh 'curl -f http://staging-api-gateway/api/users || exit 1'
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        failure {
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build failed. Check console output at ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}
```

---

## 📝 Final Checklist

### Development Checklist
- [ ] All services start successfully
- [ ] Eureka dashboard shows all 5 services registered
- [ ] API Gateway routes requests correctly
- [ ] Health checks return 200 OK
- [ ] Sample data loads correctly
- [ ] Basic CRUD operations work
- [ ] Inter-service communication functions
- [ ] Error handling works properly
- [ ] Logging is configured
- [ ] Documentation is complete

### Testing Checklist  
- [ ] Unit tests pass for all services
- [ ] Integration tests pass
- [ ] Postman collection imported and working
- [ ] API endpoints tested manually
- [ ] Error scenarios tested
- [ ] Performance benchmarks meet requirements
- [ ] Load testing completed
- [ ] Security testing performed

### Deployment Checklist
- [ ] Docker images build success