/*
# NBR Banking System Backend

## Overview
This is a comprehensive banking system backend developed for the National Bank of Rwanda (NBR) using Spring Boot. The system provides complete banking operations including customer management, savings, withdrawals, and money transfers with automated email notifications.

## Features
- Customer registration and management
- Account balance management
- Saving transactions
- Withdrawal transactions
- Money transfers between accounts
- Automated email notifications
- Transaction history tracking
- Message logging with database triggers
- RESTful API with Swagger documentation

## Database Tables Required

### 1. customers
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- first_name (VARCHAR(255), NOT NULL)
- last_name (VARCHAR(255), NOT NULL)
- email (VARCHAR(255), NOT NULL, UNIQUE)
- mobile (VARCHAR(255), NOT NULL)
- dob (DATE, NOT NULL)
- account (VARCHAR(255), NOT NULL, UNIQUE)
- balance (DECIMAL(19,2), DEFAULT 0.00)
- last_update_date_time (TIMESTAMP)

### 2. banking_transactions
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- customer_id (BIGINT, FOREIGN KEY)
- account (VARCHAR(255), NOT NULL)
- amount (DECIMAL(19,2), NOT NULL)
- type (ENUM: 'SAVING', 'WITHDRAW', 'TRANSFER')
- banking_date_time (TIMESTAMP)
- recipient_account (VARCHAR(255))
- description (TEXT)

### 3. messages
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- customer_id (BIGINT, FOREIGN KEY)
- message (TEXT, NOT NULL)
- date_time (TIMESTAMP)
- email_sent (BOOLEAN, DEFAULT FALSE)

## API Endpoints

### Customer Management
- POST /api/customers/register - Register new customer
- GET /api/customers - Get all customers
- GET /api/customers/{id} - Get customer by ID
- GET /api/customers/account/{account} - Get customer by account
- PUT /api/customers/{id} - Update customer
- DELETE /api/customers/{id} - Delete customer

### Banking Operations
- POST /api/banking/save - Save money
- POST /api/banking/withdraw - Withdraw money
- POST /api/banking/transfer - Transfer money
- GET /api/banking/transactions - Get all transactions
- GET /api/banking/transactions/{id} - Get transaction by ID
- GET /api/banking/transactions/customer/{customerId} - Get customer transactions
- GET /api/banking/transactions/account/{account} - Get account transactions

### Messages
- GET /api/messages - Get all messages
- GET /api/messages/customer/{customerId} - Get customer messages
- GET /api/messages/unsent - Get unsent messages

## Setup Instructions

### 1. Database Setup
- Install MySQL
- Create database: `nbr_banking_db`
- Run the provided SQL schema
- Update application.yml with your database credentials

### 2. Email Configuration
- Update application.yml with your SMTP settings
- For Gmail: use app password instead of regular password

### 3. Run Application
```bash
mvn spring-boot:run
```

### 4. Access Swagger UI
- Open: http://localhost:8080/swagger-ui/index.html

## Testing with Postman

### Register Customer
```json
POST /api/customers/register
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "mobile": "+250788123456",
    "dob": "1990-01-01",
    "initialBalance": 100000.00
}
```

### Save Money
```json
POST /api/banking/save
{
    "account": "NBR1234567890",
    "amount": 50000.00,
    "description": "Salary deposit"
}
```

### Withdraw Money
```json
POST /api/banking/withdraw
{
    "account": "NBR1234567890",
    "amount": 25000.00,
    "description": "ATM withdrawal"
}
```

### Transfer Money
```json
POST /api/banking/transfer
{
    "sourceAccount": "NBR1234567890",
    "recipientAccount": "NBR0987654321",
    "amount": 30000.00,
    "description": "Payment for services"
}
```

## Key Features Implemented

1. **Customer Management**: Complete CRUD operations for customer accounts
2. **Banking Operations**: Save, withdraw, and transfer with balance validation
3. **Email Notifications**: Automated emails for all transactions
4. **Database Triggers**: Automatic message creation on transactions
5. **Error Handling**: Comprehensive exception handling with proper HTTP status codes
6. **API Documentation**: Swagger/OpenAPI documentation
7. **Validation**: Input validation with proper error messages
8. **Transaction History**: Complete audit trail of all operations

## Technologies Used
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL Database
- Spring Mail
- Swagger/OpenAPI
- Maven
- Java 17

## Security Considerations
- Input validation on all endpoints
- Database constraints to prevent data corruption
- Transaction management to ensure data consistency
- Proper error handling without exposing sensitive information
  */