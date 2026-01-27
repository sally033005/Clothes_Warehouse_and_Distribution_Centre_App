# Deployment Guide

This guide covers deploying the Clothes Warehouse Management System to various cloud platforms.

## Prerequisites

- Git repository set up
- Account on chosen deployment platform
- PostgreSQL database (for production)

## Option 1: Heroku (Recommended for Beginners)

### Step 1: Prepare for Heroku

1. **Add PostgreSQL dependency** to `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. **Create `Procfile`** in project root:
```
web: java -jar target/Ass1_Clothes_Warehouse-0.0.1-SNAPSHOT.jar
```

3. **Update `application.properties`** for production:
```properties
# Production profile
spring.profiles.active=prod
spring.jpa.hibernate.ddl-auto=update
```

4. **Create `src/main/resources/application-prod.properties`**:
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### Step 2: Deploy to Heroku

```bash
# Install Heroku CLI
# Login to Heroku
heroku login

# Create Heroku app
heroku create your-app-name

# Add PostgreSQL addon
heroku addons:create heroku-postgresql:mini

# Set Java version
heroku config:set JAVA_OPTS="-Xmx512m"

# Deploy
git push heroku main

# Open app
heroku open
```

## Option 2: Railway (Modern & Easy)

1. **Sign up** at [railway.app](https://railway.app)
2. **Connect your GitHub repository**
3. **Add PostgreSQL service**
4. **Set environment variables**:
   - `DATABASE_URL` (auto-set by Railway)
   - `SPRING_PROFILES_ACTIVE=prod`
5. **Deploy** - Railway auto-detects Spring Boot and deploys

## Option 3: Render

1. **Sign up** at [render.com](https://render.com)
2. **Create new Web Service**
3. **Connect GitHub repository**
4. **Configure**:
   - Build Command: `mvn clean install`
   - Start Command: `java -jar target/Ass1_Clothes_Warehouse-0.0.1-SNAPSHOT.jar`
5. **Add PostgreSQL database**
6. **Set environment variables**:
   - `DATABASE_URL`
   - `SPRING_PROFILES_ACTIVE=prod`

## Option 4: AWS (More Professional)

### Using AWS Elastic Beanstalk

1. **Install EB CLI**:
```bash
pip install awsebcli
```

2. **Initialize EB**:
```bash
eb init -p java-17 -r us-east-1
```

3. **Create environment**:
```bash
eb create warehouse-env
```

4. **Set environment variables** in AWS Console:
   - `DATABASE_URL`
   - `SPRING_PROFILES_ACTIVE=prod`

5. **Deploy**:
```bash
eb deploy
```

### Using Docker on AWS ECS

1. **Create `Dockerfile`**:
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/Ass1_Clothes_Warehouse-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

2. **Build and push to ECR**
3. **Create ECS service** with PostgreSQL RDS

## Environment Variables

Set these in your deployment platform:

- `DATABASE_URL` - PostgreSQL connection string
- `SPRING_PROFILES_ACTIVE=prod` - Activate production profile
- `SPRING_JPA_HIBERNATE_DDL_AUTO=update` - Database schema updates

## Database Migration

For production, consider using Flyway or Liquibase for database migrations instead of `ddl-auto=update`.

## Monitoring

- Add health check endpoint: `/actuator/health`
- Set up logging: Configure log levels in `application.properties`
- Monitor errors: Use services like Sentry or Rollbar

## Security Checklist

- [ ] Change default H2 console password
- [ ] Use environment variables for sensitive data
- [ ] Enable HTTPS/SSL
- [ ] Configure CORS properly
- [ ] Set up rate limiting
- [ ] Regular security updates

## Troubleshooting

### Database Connection Issues
- Verify `DATABASE_URL` format
- Check database credentials
- Ensure database is accessible from deployment platform

### Build Failures
- Check Java version (must be 17+)
- Verify Maven dependencies
- Check build logs for specific errors

### Application Won't Start
- Check logs: `heroku logs --tail` or platform equivalent
- Verify environment variables are set
- Check port configuration (should use `PORT` env var)
