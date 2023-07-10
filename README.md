# Book-Application

### Overview

- Solution to "College Library Project" : Problem Statement 2
(Book Details App)
- Submission by team Dockerators (Kalyan Ram, Vidhish T, Vikas K)
- This project behaves as a microservice whose APIs are directly queried by our submission to Problem Statement 3 (Book Lending App)

### Functionality

- The API endpoints allows users to add, update and delete students.
- Each book has the following attributes:
  - Book Code (unique for each book)
  - Book Title
  - Book Author
  - Book Description

### Endpoints

- The following are the API endpoints in our application:
  | Method | Endpoint | Description |
  |--------|----------|--------------|
  |GET |/api/books | Returns all books that have been added to the database so far. |
  |GET| /api/books/code/{code} | Returns the book that corresponds the code in the path variable. Returns a BookNotFoundException if there is no book that corresponds to the given code. |
  |POST |/api/books | Takes a book object in the body of the request and adds it to the database. Returns a BookAlreadyExistsException if there is already a book in the DB with the same code (as book code is unique). Returns a NullFieldsException if there is any attribute of the book that is null in the request (all book fields are mandatory). |
  |PUT| /api/books | Takes a book object in the body of the request and updates the book in the DB that corresponds to the code of the book in request body to the book in the request body. Returns a BookNotFoundException if there is no book in the DB with corresponding book code. Returns a NullFieldsException if there is any attribute of the book that is null in the request (all book fields are mandatory). |
  |DELETE |/api/books/code/{code} | Deletes the book that corresponds the code in the path variable from the DB. Returns a BookNotFoundException if there is no book that corresponds to the given code. |

### Project Structure

- Follows the Controller-Service-Repository pattern.
  - The controller layer is responsible for management of the REST interface to the business logic. It defines the REST API endpoints and calls the respective service layer functions based on which endpoint is invoked. It uses the @RestController annotation to make the application RESTful.
  - The service layer handles the business logic implementation. There is an interface for the book service and is then extended with a Book Service Implementation. This is in accordance to the SOLID Principles. 
  - The repository layer handles all interaction with the database. Our application uses the JPA library to make database operations easier to code. 

### Testing

- The Testing is performed in the following way (using JUnit to automate the tests):
  - Create unit test cases for each method in the repo layer. Verify that the data access operations (CRUD) are performed correctly. These tests use the in memory H2 Database to avoid affecting the actual production database.
  - Create unit test cases for each method in the service layer. Mock the repo layer (using Mockito) to isolate the business logic.
    Test different scenarios and edge cases to validate the business rules.
  - Create integration tests to test the API endpoints provided by the controller (using mockMVC). Verify that the endpoints handle requests and responses correctly. Test different HTTP methods (GET, POST, PUT, DELETE) and verify the corresponding actions.
