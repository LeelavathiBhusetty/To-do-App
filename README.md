# Todo Application - RESTful API with Spring Boot & MongoDB

A production-ready RESTful Todo application built with Spring Boot 3.2, MongoDB, Docker, and CI/CD using GitHub Actions.

## Features

- ✅ Complete CRUD operations for Todos
- ✅ RESTful API design
- ✅ MongoDB integration with Spring Data
- ✅ Docker containerization
- ✅ Multi-stage Docker build for optimized image size
- ✅ Docker Compose for local development
- ✅ GitHub Actions CI/CD pipeline
- ✅ Automatic Docker Hub deployment
- ✅ Input validation
- ✅ Global exception handling
- ✅ Search and filter capabilities
- ✅ Health check endpoint

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data MongoDB**
- **Maven**
- **MongoDB 7.0**
- **Docker & Docker Compose**
- **GitHub Actions**
- **Lombok**

## Project Structure

```
todo-app/
├── src/
│   ├── main/
│   │   ├── java/com/todoapp/
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── model/            # Entity/Domain models
│   │   │   ├── repository/       # MongoDB repositories
│   │   │   ├── service/          # Business logic
│   │   │   ├── exception/        # Custom exceptions & handlers
│   │   │   └── TodoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .github/workflows/
│   └── ci-cd.yml                 # GitHub Actions workflow
├── Dockerfile                     # Multi-stage Docker build
├── docker-compose.yml             # Local development setup
├── pom.xml                        # Maven dependencies
└── README.md
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| GET | `/api/todos?completed=true` | Get completed todos |
| GET | `/api/todos?search=keyword` | Search todos by title |
| GET | `/api/todos/{id}` | Get todo by ID |
| POST | `/api/todos` | Create new todo |
| PUT | `/api/todos/{id}` | Update todo |
| PATCH | `/api/todos/{id}/toggle` | Toggle completion status |
| DELETE | `/api/todos/{id}` | Delete todo |
| DELETE | `/api/todos` | Delete all todos |

## API Request/Response Examples

### Create Todo
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Complete the Spring Boot tutorial",
    "completed": false
  }'
```

### Get All Todos
```bash
curl http://localhost:8080/api/todos
```

### Update Todo
```bash
curl -X PUT http://localhost:8080/api/todos/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot - Updated",
    "description": "Complete advanced Spring Boot concepts",
    "completed": true
  }'
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose
- MongoDB (optional - can use Docker)
- Git

## Local Development Setup

### Option 1: Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd todo-app
   ```

2. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

   This will:
   - Start MongoDB on port 27017
   - Build the Spring Boot application
   - Start the application on port 8080

3. **Access the application**
   ```
   http://localhost:8080/api/todos
   ```

4. **Stop the application**
   ```bash
   docker-compose down
   ```

### Option 2: Local Maven Build

1. **Install and start MongoDB locally**
   ```bash
   # On macOS
   brew install mongodb-community
   brew services start mongodb-community

   # On Linux
   sudo systemctl start mongod
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
   ```
   http://localhost:8080/api/todos
   ```

## Docker Build and Run

### Build Docker Image
```bash
docker build -t todo-app:latest .
```

### Run Docker Container
```bash
# Run MongoDB first
docker run -d --name mongodb -p 27017:27017 mongo:7.0

# Run application
docker run -d \
  --name todo-app \
  -p 8080:8080 \
  -e SPRING_DATA_MONGODB_HOST=mongodb \
  --link mongodb:mongodb \
  todo-app:latest
```

### Pull from Docker Hub (after CI/CD setup)
```bash
docker pull your-dockerhub-username/todo-app:latest
docker run -d -p 8080:8080 \
  -e SPRING_DATA_MONGODB_HOST=host.docker.internal \
  your-dockerhub-username/todo-app:latest
```

## CI/CD Setup with GitHub Actions

### 1. Create GitHub Repository
```bash
cd todo-app
git init
git add .
git commit -m "Initial commit: Todo App with Spring Boot and MongoDB"
git branch -M main
git remote add origin https://github.com/your-username/todo-app.git
git push -u origin main
```

### 2. Configure Docker Hub Secrets

1. Go to your GitHub repository
2. Navigate to **Settings** → **Secrets and variables** → **Actions**
3. Add the following secrets:
   - `DOCKER_USERNAME`: Your Docker Hub username
   - `DOCKER_PASSWORD`: Your Docker Hub access token

### 3. Update Docker Image Name

Edit `.github/workflows/ci-cd.yml` and replace:
```yaml
DOCKER_IMAGE_NAME: your-dockerhub-username/todo-app
```

### 4. Push to GitHub

Every push to `main` branch will:
- ✅ Build the Maven project
- ✅ Run tests
- ✅ Build Docker image
- ✅ Push to Docker Hub with tags: `latest`, `main-{sha}`

### 5. View CI/CD Status

Check the **Actions** tab in your GitHub repository to see the pipeline execution.

## Running from Docker Hub

Once the CI/CD pipeline has pushed the image to Docker Hub:

### Using Docker Compose
Update `docker-compose.yml` to use your Docker Hub image:
```yaml
services:
  todo-app:
    image: your-dockerhub-username/todo-app:latest
    # ... rest of configuration
```

Then run:
```bash
docker-compose up
```

### Using Docker Run
```bash
# Create network
docker network create todo-network

# Run MongoDB
docker run -d \
  --name mongodb \
  --network todo-network \
  -p 27017:27017 \
  mongo:7.0

# Run application from Docker Hub
docker run -d \
  --name todo-app \
  --network todo-network \
  -p 8080:8080 \
  -e SPRING_DATA_MONGODB_HOST=mongodb \
  your-dockerhub-username/todo-app:latest
```

## Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# MongoDB Configuration (Local)
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=tododb

# For Docker, use:
# spring.data.mongodb.host=mongodb
```

### Environment Variables (Docker)

- `SPRING_DATA_MONGODB_HOST`: MongoDB host (default: localhost)
- `SPRING_DATA_MONGODB_PORT`: MongoDB port (default: 27017)
- `SPRING_DATA_MONGODB_DATABASE`: Database name (default: tododb)

## Testing

### Run tests
```bash
mvn test
```

### Manual API Testing with curl

```bash
# Health check
curl http://localhost:8080/api/todos

# Create todo
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Todo","description":"Test Description","completed":false}'

# Get all todos
curl http://localhost:8080/api/todos

# Get specific todo
curl http://localhost:8080/api/todos/{id}

# Update todo
curl -X PUT http://localhost:8080/api/todos/{id} \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Todo","description":"Updated Description","completed":true}'

# Toggle completion
curl -X PATCH http://localhost:8080/api/todos/{id}/toggle

# Delete todo
curl -X DELETE http://localhost:8080/api/todos/{id}
```

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running: `docker ps` or `brew services list`
- Check MongoDB host configuration in `application.properties`
- For Docker: use `mongodb` as host, for local: use `localhost`

### Port Already in Use
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9

# Or change port in application.properties
server.port=8081
```

### Docker Build Issues
```bash
# Clean Maven cache
mvn clean

# Remove old Docker images
docker system prune -a
```

## Best Practices Implemented

✅ **Multi-stage Docker builds** - Smaller image size (~200MB vs 800MB+)
✅ **Non-root user** - Enhanced security in Docker container
✅ **Health checks** - Container health monitoring
✅ **Layer caching** - Faster Docker builds
✅ **Environment-based configuration** - Easy deployment across environments
✅ **Global exception handling** - Consistent error responses
✅ **Input validation** - Data integrity
✅ **RESTful conventions** - Standard HTTP methods and status codes
✅ **Lombok** - Reduced boilerplate code
✅ **CI/CD automation** - Automated testing and deployment

## Future Enhancements

- [ ] Add authentication & authorization (Spring Security)
- [ ] Implement pagination for large datasets
- [ ] Add integration tests
- [ ] Implement caching (Redis)
- [ ] Add API documentation (Swagger/OpenAPI)
- [ ] Implement monitoring (Actuator, Prometheus)
- [ ] Add logging (ELK stack)
- [ ] Deploy to Kubernetes

## License

MIT License

## Author

Prasanna Kumar

## Contributing

Pull requests are welcome! Please follow these steps:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a pull request

---

**Happy Coding!** 🚀
