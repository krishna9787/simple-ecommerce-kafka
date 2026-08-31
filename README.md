# Simple E-Commerce Kafka Microservices

A microservices-based e-commerce application built with Java, Spring Boot, and Apache Kafka for asynchronous communication between services.

## Project Overview

This project demonstrates a modern microservices architecture for an e-commerce platform with event-driven communication. The system is composed of three independent microservices that communicate through Kafka topics.

## Architecture

### Microservices

1. **Inventory Service** - Manages product inventory and stock levels
   - Location: `inventory-service/`
   - Handles inventory updates and stock queries
   - Publishes inventory events to Kafka

2. **Order Service** - Processes customer orders
   - Location: `order-service/`
   - Manages order creation and status tracking
   - Consumes inventory and payment events
   - Publishes order events to Kafka

3. **Payment Service** - Handles payment processing
   - Location: `payment-service/`
   - Processes payments for orders
   - Publishes payment status events
   - Integrates with payment providers

## Technology Stack

- **Java** - Core programming language
- **Spring Boot** - Framework for microservices
- **Apache Kafka** - Event streaming and asynchronous messaging
- **Maven** - Build and dependency management
- **JPA/Hibernate** - Object-relational mapping
- **H2/MySQL** - Database (varies by service configuration)

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher
- Apache Kafka 2.8 or higher
- Docker (optional, for containerized setup)

## Installation

### 1. Clone the Repository

```bash
cd simple-ecommerce-kafka
```

### 2. Build All Services

```bash
# Build the entire project
mvn clean install

# Or build individual services
cd inventory-service && mvn clean install
cd ../order-service && mvn clean install
cd ../payment-service && mvn clean install
```

### 3. Configure Kafka

Ensure Kafka is running on your local machine or update the `application.yaml` in each service to point to your Kafka broker:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
```

## Running the Services

### Option 1: From Command Line

Each service can be started independently:

```bash
# Terminal 1: Start Inventory Service
cd inventory-service
mvn spring-boot:run

# Terminal 2: Start Order Service
cd order-service
mvn spring-boot:run

# Terminal 3: Start Payment Service
cd payment-service
mvn spring-boot:run
```

### Option 2: Using Maven Wrapper

```bash
# Inventory Service
cd inventory-service
./mvnw spring-boot:run

# Order Service
cd order-service
./mvnw spring-boot:run

# Payment Service
cd payment-service
./mvnw spring-boot:run
```

### Option 3: Docker Compose (if applicable)

```bash
docker-compose up
```

## API Endpoints

Each service exposes REST APIs for interaction. Refer to the individual service documentation or Swagger UI (if enabled) for detailed endpoint specifications.

### Inventory Service

- Base URL: `http://localhost:8081`
- Endpoints: Product inventory management

### Order Service

- Base URL: `http://localhost:8082`
- Endpoints: Order management

### Payment Service

- Base URL: `http://localhost:8083`
- Endpoints: Payment processing

## Project Structure

```
simple-ecommerce-kafka/
├── inventory-service/       # Inventory management microservice
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── HELP.md
├── order-service/           # Order processing microservice
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── HELP.md
├── payment-service/         # Payment processing microservice
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   ├── pom.xml
│   └── HELP.md
└── README.md                # This file
```

## Kafka Topics

The services communicate through the following Kafka topics (as configured):

- `inventory-events` - Inventory updates
- `order-events` - Order status changes
- `payment-events` - Payment processing events

## Configuration

Each microservice has its own `application.yaml` configuration file located in `src/main/resources/`. Key configurations include:

- Server port
- Kafka broker addresses
- Database connection details
- Logging levels

## Development

### Adding a New Feature

1. Create a feature branch: `git checkout -b feature/your-feature-name`
2. Make your changes
3. Run tests: `mvn test`
4. Commit your changes
5. Push to the branch and create a pull request

### Running Tests

```bash
# Run tests for all services
mvn test

# Run tests for a specific service
cd inventory-service && mvn test
```

## Troubleshooting

### Kafka Connection Issues

- Ensure Kafka is running and accessible
- Verify `bootstrap-servers` configuration in `application.yaml`
- Check Kafka logs for errors

### Port Conflicts

- Services run on ports 8081, 8082, and 8083 by default
- Modify `server.port` in `application.yaml` if ports are already in use

### Build Failures

- Clean and rebuild: `mvn clean install`
- Update Maven: `mvn --version`
- Check Java version: `java -version`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write or update tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support

For issues, questions, or suggestions, please open an issue on the project repository.

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring Cloud Stream Documentation](https://spring.io/projects/spring-cloud-stream)
