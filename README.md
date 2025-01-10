# Webshop Platform

This project is a full-stack webshop application developed using **Vue**, **Spring Boot**, and **PostgreSQL**. The platform provides functionalities for user authentication, product management, and order handling. It utilizes JWT for secure authentication and follows a modular structure to separate concerns between frontend and backend.

---

## 📋 Prerequisites

Ensure the following tools are installed:

- **Java**: Version 17 or higher
- **Maven**: For backend dependency management and building
- **Node.js**: Version 16 or higher, including npm
- **Docker**: For running the PostgreSQL database
- **PostgreSQL**: Database running inside a Docker container
- **IntelliJ IDEA**: Recommended development environment for backend
- **Git**: For version control
- **Vue CLI**: For frontend development

---

## ⚙️ Technologies

### Frontend:
- **Vue**: Framework for building the user interface
- **Vue Router**: For managing routes
- **Pinia**: State management
- **Axios**: HTTP client for API requests
- **Bootstrap**: For styling and UI components

### Backend:
- **Spring Boot**: Framework for developing REST APIs
- **PostgreSQL**: Relational database
- **JWT (JSON Web Tokens)**: For authentication and authorization
- **Docker**: For database containerization

---

## 🚀 Project Setup

### Backend Setup

1. **Clone the Repository**
   ```bash
   git clone https://your-repository-url
   ```

2. **Install Dependencies**
   The project uses Maven. Ensure all dependencies are downloaded:
   ```bash
   mvn clean install
   ```

3. **Start the Docker Container**
   A Docker container is used for the PostgreSQL database. Create and start the container using the following command:
   ```bash
   docker-compose up -d
   ```
   > **Note**: The `docker-compose.yaml` file is included in the project.

4. **Configure the Database**
   The database connection is defined in the `application.properties` file. Default configuration:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/webshop
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```
   Modify these values if necessary.

5. **Run the Backend**
   Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will be accessible at `http://localhost:8080`.

### Frontend Setup

1. **Navigate to the Frontend Directory**
   ```bash
   cd frontend
   ```

2. **Install Dependencies**
   Ensure all dependencies are installed:
   ```bash
   npm install
   ```

3. **Run the Frontend**
   Start the development server:
   ```bash
   npm run serve
   ```

4. **Build for Production**
   Compile and minify for production:
   ```bash
   npm run build
   ```

5. **Lint and Fix Files**
   Run the linter and fix files:
   ```bash
   npm run lint
   ```

6. **Customize Configuration**
   See [Configuration Reference](https://cli.vuejs.org/config/).

The frontend will be accessible at `http://localhost:8081`.

---

## 📁 Project Structure

### Backend:
```
src/
├── main/
   ├── java/at/technikum/springrestbackend/
   │   ├── config/          // Configuration setup
   │   ├── controller/      // REST controllers (e.g., AuthController, ProductController)
   │   ├── dto/             // Data Transfer Objects
   │   ├── entity/          // Entity definitions
   │   ├── repository/      // Database repositories
   │   ├── security/        // Security configurations (JWT)
   │   └── service/         // Business logic services
   └── resources/
       ├── application.properties // Configuration file
       └── db/migration/          // Flyway migrations
├── test/
   ├── java/at/technikum/springrestbackend/
       ├── ControllerTest/   // Tests for REST controllers
       ├── SecurityTest/     // Tests for security configurations
       └── ServiceTest/      // Tests for service logic
```

### Frontend:
```
frontend/
├── src/
   ├── assets/            // Static assets
   ├── components/        // Vue components
   ├── router/            // Vue Router configurations
   ├── store/             // Pinia state management
   ├── views/             // Application views
   ├── App.vue            // Main Vue component
   └── main.js            // Application entry point
```

---

## 📡 API Documentation

### **AuthController** `/api/auth`
- `POST /token`: Authenticate user and return JWT token.

### **UserController** `/api/users`
- `POST /`: Register a new user.
- `GET /{id}`: Retrieve user details by ID.
- `GET /admin`: Retrieve all users (admin only).
- `PUT /{id}`: Update user profile.
- `DELETE /admin/{id}`: Delete user (admin only).

### **ProductController** `/api/products`
- `POST /add`: Add a new product (admin only).
- `GET /loadAll`: Retrieve all products.
- `GET /{id}`: Retrieve product details by ID.
- `PUT /{id}`: Update product details (admin only).
- `DELETE /{id}`: Delete product (admin only).

### **OrderController** `/api/orders`
- `POST /place`: Place a new order.
- `GET /my-orders`: Retrieve orders for the current user.
- `GET /all`: Retrieve all orders (admin only).
- `PUT /{id}`: Update order status (admin only).
- `DELETE /{id}`: Delete order (admin only).

---

## 🔐 Roles and Permissions

- **Admin**:
   - Can manage users, products, and orders.
   - Full access to all endpoints.
- **User**:
   - Can register, view products, and place orders.
   - Limited access to personal data and orders.

---

## ▶️ How to Run

1. **Start Backend**: Follow the backend setup steps to start the Spring Boot application.
2. **Start Frontend**: Follow the frontend setup steps to start the Vue application.
3. Access the application:
   - Frontend: `http://localhost:8081`
   - Backend: `http://localhost:8080`

---

