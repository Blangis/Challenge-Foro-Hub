# ForoHub API

Small REST API built with **Spring Boot** that simulates a forum where users can create and manage topics.
This project was developed to practice backend concepts such as **REST APIs, authentication with JWT, database migrations, and Spring Security**.

## Technologies

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT
* Flyway
* MySQL
* Maven

## Main Features

* User login with JWT authentication
* Protected API endpoints
* Create and list topics
* Database migrations using Flyway

## Running the Project

1. Create a MySQL database called:

```
forohub
```

2. Configure your database credentials in:

```
application.properties
```

3. Run the application:

```
mvn spring-boot:run
```

The API will start at:

```
http://localhost:8080
```

## Example Login Request

```
POST /login
```

Body:

```
{
 "email": "user@email.com",
 "password": "password"
}
```

The response returns a **JWT token** that must be included in requests:

```
Authorization: Bearer <token>
```

## Author

Giselle Licona
