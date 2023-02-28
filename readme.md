A simple REST API built using the Spring Boot framework.

The local server is found found at http://localhost:8080

Database is initalised with records. Supports Create, Read, Update and Delete at /api/books:
```
GET by id: /api/books/{id}
GET all: /api/books
PUT: /api/books/{id}
POST: /api/books
DELETE: /api/books/{id}
```

Build Maven project and run standalone jar:
```
./mvnw package
java -jar ./target/FrogBackendTest.jar
```
Run JUnit tests:
```
./mvnw test
```

test
