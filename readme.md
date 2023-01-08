The REST API is found at http://localhost:8080

Database is initalised with records. Supports Create, Read, Update and Delete at /api/books:

GET by ID: /api/books/{id}
GET all books: /api/books
PUT: /api/books/{id}
POST at /api/books
DELETE at /api/books/{id}


Build Maven project and run standalone jar:
```
./mvnw package
java -jar ./target/FrogBackendTest.jar
```
Run JUnit tests:
```
./mvnw test
```

Using Postman