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
  - Responsive design for all devices
  - Modern CSS styling with gradients and animations
  - Intuitive navigation and user feedback
  - Form validation with real-time feedback

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.4.2, Java 17
- **Security**: Spring Security with BCrypt password encoding
- **Database**: H2 (development)
- **ORM**: Spring Data JPA / Hibernate
- **Frontend**: Thymeleaf templates, HTML5, CSS3
- **Build Tool**: Maven
- **Other**: Lombok, REST Template for API integration

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- (Optional) PostgreSQL for production deployment

## 🔧 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Ass1_Clothes_Warehouse
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Main application: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:file:./data/tekkenreborn`
     - Username: `sa`
     - Password: (leave empty)

5. **Default Users**
   - Register a new user through the registration page
   - First user can be set as ADMIN role

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
