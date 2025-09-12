# setup-microservices.ps1
# Create Banking Microservices Project Structure

$root = "banking-microservices"
$folders = @(
    # Root files
    "$root",

    # api-gateway
    "$root/api-gateway/src/main/java/com/banking/gateway/config",
    "$root/api-gateway/src/main/java/com/banking/gateway/filter",
    "$root/api-gateway/src/main/resources",

    # eureka-server
    "$root/eureka-server/src/main/java/com/banking/eureka",
    "$root/eureka-server/src/main/resources",

    # user-service
    "$root/user-service/src/main/java/com/banking/user/controller",
    "$root/user-service/src/main/java/com/banking/user/model",
    "$root/user-service/src/main/java/com/banking/user/service",
    "$root/user-service/src/main/java/com/banking/user/repository",
    "$root/user-service/src/main/java/com/banking/user/config",
    "$root/user-service/src/main/resources",

    # account-service
    "$root/account-service/src/main/java/com/banking/account/controller",
    "$root/account-service/src/main/java/com/banking/account/model",
    "$root/account-service/src/main/java/com/banking/account/service",
    "$root/account-service/src/main/java/com/banking/account/repository",
    "$root/account-service/src/main/java/com/banking/account/client",
    "$root/account-service/src/main/java/com/banking/account/config",
    "$root/account-service/src/main/resources",

    # transaction-service
    "$root/transaction-service/src/main/java/com/banking/transaction/controller",
    "$root/transaction-service/src/main/java/com/banking/transaction/model",
    "$root/transaction-service/src/main/java/com/banking/transaction/service",
    "$root/transaction-service/src/main/java/com/banking/transaction/repository",
    "$root/transaction-service/src/main/java/com/banking/transaction/client",
    "$root/transaction-service/src/main/java/com/banking/transaction/config",
    "$root/transaction-service/src/main/resources",

    # notification-service
    "$root/notification-service/src/main/java/com/banking/notification/controller",
    "$root/notification-service/src/main/java/com/banking/notification/model",
    "$root/notification-service/src/main/java/com/banking/notification/service",
    "$root/notification-service/src/main/java/com/banking/notification/repository",
    "$root/notification-service/src/main/java/com/banking/notification/config",
    "$root/notification-service/src/main/resources"
)

# Create all folders
foreach ($folder in $folders) {
    New-Item -ItemType Directory -Path $folder -Force | Out-Null
}

# Create placeholder files
$files = @(
    "$root/README.md",
    "$root/docker-compose.yml",

    "$root/api-gateway/pom.xml",
    "$root/api-gateway/src/main/java/com/banking/gateway/ApiGatewayApplication.java",
    "$root/api-gateway/src/main/java/com/banking/gateway/config/GatewayConfig.java",
    "$root/api-gateway/src/main/java/com/banking/gateway/filter/AuthenticationFilter.java",
    "$root/api-gateway/src/main/resources/application.yml",

    "$root/eureka-server/pom.xml",
    "$root/eureka-server/src/main/java/com/banking/eureka/EurekaServerApplication.java",
    "$root/eureka-server/src/main/resources/application.yml",

    "$root/user-service/pom.xml",
    "$root/user-service/src/main/java/com/banking/user/UserServiceApplication.java",
    "$root/user-service/src/main/java/com/banking/user/controller/UserController.java",
    "$root/user-service/src/main/java/com/banking/user/model/User.java",
    "$root/user-service/src/main/java/com/banking/user/service/UserService.java",
    "$root/user-service/src/main/java/com/banking/user/service/UserServiceImpl.java",
    "$root/user-service/src/main/java/com/banking/user/repository/UserRepository.java",
    "$root/user-service/src/main/java/com/banking/user/config/DataInitializer.java",
    "$root/user-service/src/main/resources/application.yml",

    "$root/account-service/pom.xml",
    "$root/account-service/src/main/java/com/banking/account/AccountServiceApplication.java",
    "$root/account-service/src/main/java/com/banking/account/controller/AccountController.java",
    "$root/account-service/src/main/java/com/banking/account/model/Account.java",
    "$root/account-service/src/main/java/com/banking/account/service/AccountService.java",
    "$root/account-service/src/main/java/com/banking/account/service/AccountServiceImpl.java",
    "$root/account-service/src/main/java/com/banking/account/repository/AccountRepository.java",
    "$root/account-service/src/main/java/com/banking/account/client/UserServiceClient.java",
    "$root/account-service/src/main/java/com/banking/account/config/DataInitializer.java",
    "$root/account-service/src/main/resources/application.yml",

    "$root/transaction-service/pom.xml",
    "$root/transaction-service/src/main/java/com/banking/transaction/TransactionServiceApplication.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/controller/TransactionController.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/model/Transaction.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/model/TransactionType.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/service/TransactionService.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/service/TransactionServiceImpl.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/repository/TransactionRepository.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/client/AccountServiceClient.java",
    "$root/transaction-service/src/main/java/com/banking/transaction/config/DataInitializer.java",
    "$root/transaction-service/src/main/resources/application.yml",

    "$root/notification-service/pom.xml",
    "$root/notification-service/src/main/java/com/banking/notification/NotificationServiceApplication.java",
    "$root/notification-service/src/main/java/com/banking/notification/controller/NotificationController.java",
    "$root/notification-service/src/main/java/com/banking/notification/model/Notification.java",
    "$root/notification-service/src/main/java/com/banking/notification/model/NotificationType.java",
    "$root/notification-service/src/main/java/com/banking/notification/service/NotificationService.java",
    "$root/notification-service/src/main/java/com/banking/notification/service/NotificationServiceImpl.java",
    "$root/notification-service/src/main/java/com/banking/notification/repository/NotificationRepository.java",
    "$root/notification-service/src/main/java/com/banking/notification/config/DataInitializer.java",
    "$root/notification-service/src/main/resources/application.yml"
)

foreach ($file in $files) {
    New-Item -ItemType File -Path $file -Force | Out-Null
}

Write-Host "✅ Banking Microservices folder structure created successfully."
