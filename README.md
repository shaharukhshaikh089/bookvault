# 📚 BookVault - Library Management System

## 🚀 Overview

BookVault is a backend REST API for managing a library system, built using Spring Boot 3.x.
It supports book management, member management, and loan tracking with secure JWT-based authentication and role-based access control.

The application is designed with production-ready practices such as layered architecture, validation, transactions, caching, and testing.

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot 3.x
* Spring Data JPA
* Spring Security (JWT)
* H2 In-Memory Database
* Caffeine Cache
* Maven

---

## ▶️ How to Run

```bash
git clone <your-repo-link>
cd bookvault
./mvnw spring-boot:run
```

* Application URL: http://localhost:8080
* H2 Console: http://localhost:8080/h2-console

---

## 🔐 Authentication

### Default Users (seeded via data.sql)

| Role      | Email                                           | Password |
| --------- | ----------------------------------------------- | -------- |
| LIBRARIAN | [librarian@test.com](mailto:librarian@test.com) | password |
| MEMBER    | [member@test.com](mailto:member@test.com)       | password |

### Login Endpoint

POST /api/auth/login

Use the returned JWT token:

```
Authorization: Bearer <token>
```

---

## 📦 API Features

### Books

* CRUD operations
* Dynamic filtering by:

  * genre
  * author
  * availability
* Cached using Caffeine

### Members

* CRUD operations
* Search by name or email

### Loans

* Borrow a book

  * Decrements availableCopies
  * Fails if no copies available
  * Fails if member is SUSPENDED
  * Max 3 active loans per member

* Return a book

  * Marks loan as RETURNED
  * Increments availableCopies

* Get overdue loans (sortable & pageable)

* Get loan history by member

---

## ⚙️ Key Features

### ✅ Validation & Data Integrity

* Bean Validation on all DTOs
* ISBN format enforced: `978-XXXXXXXXXX`
* Business rules enforced at service layer

### 🔒 Security

* JWT-based authentication
* Role-based authorization:

  * LIBRARIAN → full access
  * MEMBER → view books, own loans, return own books

### 🔍 Dynamic Queries

* Implemented using Spring Data JPA Specifications
* No hardcoded query combinations

### 🔁 Transactions

* Borrow and return operations are transactional
* Ensures rollback on failure scenarios

### ⚡ Async / Scheduled Processing

* Scheduled job scans and updates overdue loans
  *(or event-based handling if implemented)*

### 🧠 Caching

* `GET /api/books` is cached using Caffeine
* Cache eviction strategy:

  * Evicted on book create, update, and delete to maintain consistency

---

## 🧪 Testing

The project includes:

* `@WebMvcTest` → controller layer testing
* `@DataJpaTest` → repository testing (custom queries/specifications)
* `@SpringBootTest` → integration test (borrow → return flow)
* Negative test → borrowing when no copies are available

---

## 🗄️ Database

* H2 in-memory database (no setup required)
* Schema initialized using SQL scripts
* Easily configurable to PostgreSQL via application properties

---

## ⚖️ Design Decisions

* Layered architecture: Controller → Service → Repository
* DTOs used to separate API contracts from entities
* UUID used as primary keys for scalability
* Specifications used for flexible querying
* Global exception handling via `@ControllerAdvice` with consistent response format

---

## 🔄 Trade-offs

* Used H2 for simplicity instead of PostgreSQL
* Basic JWT implementation without refresh tokens
* Swagger and Docker not included to focus on core backend requirements within time constraints

---

## 📬 Submission

GitHub Repository: <your-repo-link>

---

## 👨‍💻 Author

Shaharukh Shaikh
