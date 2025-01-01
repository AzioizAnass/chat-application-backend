# Blog-Chat Application

A real-time blog and chat application built with Spring Boot 3.2.0 and Java 17.

Repository: [https://github.com/AzioizAnass/chat-application-backend](https://github.com/AzioizAnass/chat-application-backend)

## 🚀 Features

- Secure JWT Authentication
- Blog System
- Real-time Chat
- API Documentation with Swagger
- User Management
- REST API Interface

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - For authentication and authorization
- **Spring Data JPA** - For data persistence
- **MySQL** - Database
- **Lombok** - To reduce boilerplate code
- **MapStruct** - For object mapping
- **Swagger** - For API documentation

## 📋 Prerequisites

- Java JDK 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

## 🚀 Installation and Setup

1. Clone the repository:
```bash
git clone https://github.com/AzioizAnass/chat-application-backend.git
```

2. Configure MySQL database in `application.properties`

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`
Swagger documentation will be available at: `http://localhost:8080/swagger-ui.html`

## 🔐 Security Configuration

The application uses JWT (JSON Web Tokens) for authentication. Make sure to configure the appropriate security properties in the configuration file.

## 📝 API Endpoints

### Authentication (`/api/auth`)
- POST `/api/auth/signin` - User login
- POST `/api/auth/signup` - Register new user

### Users (`/api/users`)
- GET `/api/users` - Get all users
- GET `/api/users/{id}` - Get user by ID
- POST `/api/users` - Create a new user
- PUT `/api/users/{id}` - Update user
- DELETE `/api/users/{id}` - Delete user

### Articles (`/api/articles`)
- GET `/api/articles` - Get all articles
- GET `/api/articles/{id}` - Get article by ID
- POST `/api/articles` - Create new article
- PUT `/api/articles/{id}` - Update article
- DELETE `/api/articles/{id}` - Delete article

### Comments (`/api/comments`)
- GET `/api/comments` - Get all comments
- GET `/api/comments/{id}` - Get comment by ID
- POST `/api/comments` - Create new comment
- PUT `/api/comments/{id}` - Update comment
- DELETE `/api/comments/{id}` - Delete comment

### Chat
- WebSocket `/chat.sendMessage` - Send a private message
- WebSocket `/chat.addUser` - Add user to chat
- WebSocket `/chat.disconnect` - Disconnect user from chat
- GET `/api/messages/{user1}/{user2}` - Get conversation history between two users

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.
