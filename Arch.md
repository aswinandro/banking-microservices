banking-microservices/
├── README.md
├── docker-compose.yml
├── api-gateway/
│   ├── pom.xml
│   ├── src/main/java/com/banking/gateway/
│   │   ├── ApiGatewayApplication.java
│   │   ├── config/
│   │   │   └── GatewayConfig.java
│   │   └── filter/
│   │       └── AuthenticationFilter.java
│   └── src/main/resources/
│       └── application.yml
├── eureka-server/
│   ├── pom.xml
│   ├── src/main/java/com/banking/eureka/
│   │   └── EurekaServerApplication.java
│   └── src/main/resources/
│       └── application.yml
├── user-service/
│   ├── pom.xml
│   ├── src/main/java/com/banking/user/
│   │   ├── UserServiceApplication.java
│   │   ├── controller/
│   │   │   └── UserController.java
│   │   ├── model/
│   │   │   └── User.java
│   │   ├── service/
│   │   │   ├── UserService.java
│   │   │   └── UserServiceImpl.java
│   │   ├── repository/
│   │   │   └── UserRepository.java
│   │   └── config/
│   │       └── DataInitializer.java
│   └── src/main/resources/
│       └── application.yml
├── account-service/
│   ├── pom.xml
│   ├── src/main/java/com/banking/account/
│   │   ├── AccountServiceApplication.java
│   │   ├── controller/
│   │   │   └── AccountController.java
│   │   ├── model/
│   │   │   └── Account.java
│   │   ├── service/
│   │   │   ├── AccountService.java
│   │   │   └── AccountServiceImpl.java
│   │   ├── repository/
│   │   │   └── AccountRepository.java
│   │   ├── client/
│   │   │   └── UserServiceClient.java
│   │   └── config/
│   │       └── DataInitializer.java
│   └── src/main/resources/
│       └── application.yml
├── transaction-service/
│   ├── pom.xml
│   ├── src/main/java/com/banking/transaction/
│   │   ├── TransactionServiceApplication.java
│   │   ├── controller/
│   │   │   └── TransactionController.java
│   │   ├── model/
│   │   │   ├── Transaction.java
│   │   │   └── TransactionType.java
│   │   ├── service/
│   │   │   ├── TransactionService.java
│   │   │   └── TransactionServiceImpl.java
│   │   ├── repository/
│   │   │   └── TransactionRepository.java
│   │   ├── client/
│   │   │   └── AccountServiceClient.java
│   │   └── config/
│   │       └── DataInitializer.java
│   └── src/main/resources/
│       └── application.yml
└── notification-service/
    ├── pom.xml
    ├── src/main/java/com/banking/notification/
    │   ├── NotificationServiceApplication.java
    │   ├── controller/
    │   │   └── NotificationController.java
    │   ├── model/
    │   │   ├── Notification.java
    │   │   └── NotificationType.java
    │   ├── service/
    │   │   ├── NotificationService.java
    │   │   └── NotificationServiceImpl.java
    │   ├── repository/
    │   │   └── NotificationRepository.java
    │   └── config/
    │       └── DataInitializer.java
    └── src/main/resources/
        └── application.yml