# Campus Backend - Spring Boot Application

A comprehensive Spring Boot backend for the campus portal application providing REST APIs for campus locations and services.

## Features

- **Campus Map Module**: Manage campus locations and categories
- **H2 In-Memory Database**: Quick development and testing
- **REST API**: RESTful endpoints for frontend integration
- **Sample Data Loader**: Pre-populated with sample campus data

## Technologies

- **Spring Boot 3.2.0**
- **Spring Data JPA**: Database access and ORM
- **H2 Database**: In-memory database for development
- **Lombok**: Reduce boilerplate code
- **Maven**: Build and dependency management

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Navigate to the backend directory:
   ```bash
   cd Projects/capmass-backend/backend
   ```

2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

3. The application will start on `http://localhost:8080`

### Building the Application

To build the application without running it:
```bash
mvn clean package
```

### Running Tests

```bash
mvn test
```

## API Endpoints

### Location Categories

**Get All Categories**
```
GET /api/categories
```

Response:
```json
[
  {
    "id": 1,
    "name": "Academic Buildings",
    "description": "Buildings for classes and lectures"
  }
]
```

### Locations

**Get All Locations**
```
GET /api/locations
```

Response:
```json
[
  {
    "id": 1,
    "name": "Engineering Building",
    "description": "Main building for engineering departments",
    "category": {
      "id": 1,
      "name": "Academic Buildings",
      "description": "Buildings for classes and lectures"
    },
    "latitude": 40.7128,
    "longitude": -74.0060
  }
]
```

**Get Locations by Category**
```
GET /api/locations/category/{categoryId}
```

Example: `GET /api/locations/category/1`

## H2 Console

The H2 database console is enabled for development and debugging.

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:campusdb`
- **Username**: `sa`
- **Password**: (leave empty)

## Database Schema

### Location Categories Table
| Column      | Type    | Description                |
|-------------|---------|----------------------------|
| id          | Long    | Primary key (auto-increment)|
| name        | String  | Category name (unique)     |
| description | String  | Category description       |

### Locations Table
| Column      | Type    | Description                |
|-------------|---------|----------------------------|
| id          | Long    | Primary key (auto-increment)|
| name        | String  | Location name              |
| description | String  | Location description       |
| category_id | Long    | Foreign key to category    |
| latitude    | Double  | Latitude coordinate        |
| longitude   | Double  | Longitude coordinate       |

## Sample Data

The application comes pre-loaded with sample data including:

**Categories:**
- Academic Buildings
- Dining
- Library
- Sports & Recreation
- Residence Halls

**Locations:**
- 3 Academic buildings
- 2 Dining facilities
- 2 Libraries
- 2 Sports facilities
- 2 Residence halls

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/capmass/backend/
│   │   │   ├── BackendApplication.java      # Main application class
│   │   │   ├── config/
│   │   │   │   └── DataLoader.java          # Sample data loader
│   │   │   ├── controller/
│   │   │   │   └── LocationController.java  # REST API controller
│   │   │   ├── entity/
│   │   │   │   ├── Location.java            # Location entity
│   │   │   │   └── LocationCategory.java    # Category entity
│   │   │   └── repository/
│   │   │       ├── LocationRepository.java
│   │   │       └── LocationCategoryRepository.java
│   │   └── resources/
│   │       └── application.properties       # Application configuration
│   └── test/
│       └── java/com/capmass/backend/
│           └── BackendApplicationTests.java # Unit tests
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

## Future Enhancements

This backend is designed to be extensible for additional features:
- Events management module
- Community features
- User authentication and authorization
- Additional location services (directions, hours, etc.)
- File upload for location images

## Configuration

Application configuration is located in `src/main/resources/application.properties`.

Key configurations:
- Database URL: `spring.datasource.url=jdbc:h2:mem:campusdb`
- H2 Console: `spring.h2.console.enabled=true`
- JPA DDL: `spring.jpa.hibernate.ddl-auto=create-drop`

## License

This project is part of the project-locallert repository.
