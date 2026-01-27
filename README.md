# Sally's Clothes Warehouse Management System

A full-stack web application for managing a luxury clothing warehouse inventory with role-based access control, built with Spring Boot.

## 🚀 Features

- **User Authentication & Authorization**
  - User registration with validation
  - Role-based access control (Admin, Warehouse Employee, Regular User)
  - Secure password encryption with BCrypt
  - Session management

- **Inventory Management**
  - Add, view, and delete clothing items
  - Filter items by brand (Balenciaga, Stone Island, Dior)
  - Pagination for large datasets
  - Item validation (name, brand, year, price)

- **Admin Dashboard**
  - Complete item management interface
  - Distribution centre integration
  - Item request system
  - User management capabilities

- **Distribution Centre Integration**
  - REST API integration with distribution centre manager
  - View available items across multiple centres
  - Request items from distribution centres

- **Modern UI/UX**
  - Responsive design with a professional **Hamburger Menu** on mobile
  - Luxury branding with high-quality imagery and consistent styling
  - Animated interactions and intuitive navigation

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.4.2, Java 17
- **Security**: Spring Security with BCrypt password encoding
- **Database**: PostgreSQL (Dockerized) / H2 (Development)
- **ORM**: Spring Data JPA / Hibernate
- **Frontend**: Thymeleaf templates, HTML5, CSS3
- **Containerization**: Docker & Docker Compose
- **Build Tool**: Maven

## 📋 Prerequisites

- Docker and Docker Compose
- Java 17 or higher (for local development)
- Maven 3.6+ (for local development)

## 🔧 Setup Instructions (Recommended: Docker)

The easiest way to run the entire system (Warehouse App, Distribution Manager, and PostgreSQL) is using Docker Compose:

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Clothes_Warehouse_and_Distribution_Centre_App
   ```

2. **Run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Access the services**
   - **Warehouse App**: http://localhost:8080
   - **Distribution Manager**: http://localhost:8081

4. **Database Initialization**
   The `docker-compose` setup automatically initializes the required PostgreSQL databases. Data deduplication guards are in place to ensure a clean state on every startup.

## 🔧 Local Development (Manual)

If you prefer to run the applications individually:

1. **Warehouse App**
   ```bash
   cd Ass1_Clothes_Warehouse/Ass1_Clothes_Warehouse
   mvn spring-boot:run
   ```

2. **Distribution Manager**
   ```bash
   cd distribution-centre-manager/distribution-centre-manager
   mvn spring-boot:run
   ```

## 🎯 User Roles

- **ADMIN**: Full access to all features including item management and distribution centres
- **WAREHOUSE_EMPLOYEE**: Can add items to inventory
- **USER**: Can view items and browse inventory

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/CPAN228/Ass1_Clothes_Warehouse/
│   │       ├── controller/     # REST controllers
│   │       ├── data/          # Repositories and data initialization
│   │       ├── model/         # Entity models
│   │       └── security/      # Security configuration
│   └── resources/
│       ├── static/           # CSS, images
│       └── templates/       # Thymeleaf templates
└── test/                     # Unit tests
```

## 🔐 Security Features

- Password encryption with BCrypt
- CSRF protection
- Role-based authorization
- Secure session management
- Input validation and sanitization

## 🌐 API Endpoints

- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /registration` - Registration page
- `POST /registration` - Process registration
- `GET /items` - List all items (paginated)
- `GET /items/brand/{brand}` - Filter items by brand
- `GET /add-item` - Add item form (Admin/Warehouse Employee)
- `POST /add-item` - Process item creation
- `GET /admin` - Admin dashboard
- `GET /admin/items-management` - Item management interface
- `GET /admin/distribution-centres` - View distribution centres

## 🔄 Future Enhancements

- [ ] Add unit and integration tests
- [ ] Implement search functionality
- [ ] Add image upload for items
- [ ] Email notifications
- [ ] Export inventory reports
- [ ] Mobile app integration
- [ ] Real-time inventory updates
