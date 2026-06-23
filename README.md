# Warranty Tracker

A web app for tracking product warranties and warranty claims. Users register products with their purchase and warranty dates, attach a vendor, and file claims against those products as issues arise.

---

## Features

**Users**
- Register with a unique username and unique email; passwords are hashed with BCrypt.
- Session-based login; unauthenticated requests are redirected to the login page.
- All data (products, vendors, claims) is scoped to the logged-in user.

**Products**
- Register a product with a serial number, description, purchase date, warranty window, receipt location, and vendor.
- Serial number must be unique per user.
- Purchase date must be past or present; warranty end date must be after warranty start date.
- Warranty status is derived at runtime: `ACTIVE`, `EXPIRING_SOON` (within 60 days of expiry), or `EXPIRED`.
- **Not implemented:** a warranty alert dashboard section that would surface all products with `EXPIRING_SOON` status is stubbed in the UI but has no backing logic yet.
- A product cannot be deleted while it has an active or pending claim.
- Deleting a product cascades to any resolved or rejected claims on it.

**Vendors**
- Create a new vendor or select an existing one when registering or editing a product.
- Vendors are scoped per user.

**Warranty Claims**
- File a claim against a product with a fault description.
- Only one `ACTIVE` or `PENDING` claim can exist per product at a time.
- Claim status transitions:
  - `PENDING` → `ACTIVE` or `REJECTED`
  - `ACTIVE` → `RESOLVED` or `REJECTED`
  - `REJECTED` → `PENDING`
  - `RESOLVED` → `PENDING`
- Re-filing a claim (transitioning any status back to `PENDING`) resets `dateFiled` to today.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.0 |
| Web / Templates | Spring MVC, Thymeleaf |
| Persistence | Spring Data JPA, Hibernate, MySQL |
| Security | Spring Security Crypto (BCrypt) |
| Validation | Jakarta Bean Validation, custom validators |
| Utilities | Lombok |
| Build | Maven |

---

## Prerequisites

- JDK 17 or later
- Maven 3.6+
- MySQL 8 running locally (or adjust the datasource URL)

---

## Configuration

Copy `src/main/resources/application.properties.example` to `application.properties` and fill in the values below.

| Property | Description |
|---|---|
| `spring.datasource.url` | JDBC URL, e.g. `jdbc:mysql://localhost:3306/warranty_tracker` |
| `spring.datasource.username` | MySQL username |
| `spring.datasource.password` | MySQL password |
| `spring.jpa.hibernate.ddl-auto` | `update` creates/updates the schema on startup |

---

## Project Structure

```
src/main/java/bg/softuni/warranty_tracker/
├── config/         # Spring beans (PasswordEncoder, MVC interceptor registration)
├── constant/       # String constants for log messages, error messages, validation messages
├── mapper/         # MapStruct-style manual mappers (entity ↔ DTO)
├── model/
│   ├── dto/        # Request/response DTOs per feature
│   └── entity/     # JPA entities (User, Product, Vendor, Claim)
├── repository/     # Spring Data JPA repositories (one per entity)
├── security/       # SessionUtils, SessionInterceptor
├── service/        # Business logic (one service per entity)
├── validation/     # Custom constraint annotations and validators
└── web/            # @Controller classes (UserController, ProductController, ClaimController, DashboardController)

src/main/resources/
├── templates/      # Thymeleaf HTML templates
└── application.properties
```

---

## Data Model

```
┌──────────┐        ┌──────────────┐        ┌─────────────────────┐
│   User   │        │   Product    │        │        Claim        │
│──────────│        │──────────────│        │─────────────────────│
│ id (UUID)│◄───┐   │ id (UUID)    │◄──┐    │ id (UUID)           │
│ username │    │   │ serialNumber │   │    │ status (enum)       │
│ email    │    │   │ description  │   └────│ faultDescription    │
│ password │    │   │ purchaseDate │        │ dateFiled           │
└──────────┘    │   │ warrantyStart│        └─────────────────────┘
                │   │ warrantyEnd  │
                │   │ receiptLoc   │
┌──────────┐    │   │ vendor_id FK │
│  Vendor  │    └───│ user_id FK   │
│──────────│        └──────────────┘
│ id (UUID)│
│ name     │◄── Product.vendor_id
│ contact* │
│ user_id  │
└──────────┘

* Contact is an embedded value object (phone, email) stored in the vendors table.

Claim status transitions:
  PENDING ──► ACTIVE ──► RESOLVED
     ▲           │            │
     │           ▼            ▼
     └────── REJECTED ◄───────┘
```

---

## Exam Requirements Evaluation

_Spring Fundamentals — Regular Exam, June 2026_

### Passing

| Requirement | Evidence |
|---|---|
| Java 17, Spring Boot 3.4.0, Maven, MySQL | `pom.xml` + `application.properties` |
| At least 3 domain entities | `User`, `Product`, `Vendor`, `Claim` (4 entities) |
| Exactly 1 JPA Repository per entity | `UserRepository`, `ProductRepository`, `VendorRepository`, `ClaimRepository` |
| At least 1 Service per entity | `UserService`, `ProductService`, `VendorService`, `ClaimService` |
| UUID primary key on every entity | `@GeneratedValue(strategy = GenerationType.UUID)` on all 4 |
| Spring Data JPA | All repositories extend `JpaRepository` |
| At least 1 entity relationship | `Product → Vendor`, `Claim → Product`, `Product → User` |
| Passwords hashed | BCrypt via `spring-security-crypto`; `UserService` calls `encoder.encode()` |
| At least 6 complete web pages (4+ dynamic) | 8 Thymeleaf pages: login, register, dashboard, register-product, edit-product, claims, add-claim, edit-claim |
| At least 4 valid domain functionalities | Register product, Edit product, Delete product, Add claim, Edit claim, Delete claim (6 total) |
| Full CRUD for at least one main entity | `Product` and `Claim` both have create / read / edit / delete |
| Session-based login (user_id in session) | `SessionUtils` + `SessionInterceptor` |
| Guests restricted to register / login | `SessionInterceptor` redirects unauthenticated requests to `/users/login` |
| Server-side validation on every form | `@Valid` on all controller methods; DTOs use `@NotBlank`, `@Size`, `@Email`, `@NotNull`; 3 custom validators |
| Validation error messages per field | Thymeleaf `th:errors` blocks on all form templates |
| README with tech stack, features, functionalities | This file |
| At least 10 commits across 3+ days | 39 commits; active across 9 distinct days (May 30 – Jun 23) |

### Failing

| Requirement | Gap |
|---|---|
| **Role-based access control** | No user roles exist. All authenticated users are equivalent — no `UserRole` enum, no admin / user distinction. The requirement explicitly demands role checks (e.g. only the resource owner or an admin can perform certain actions). |
| **Custom exceptions** | Services throw bare `new RuntimeException(message)`. The requirement says to create and throw custom exception classes for business rule violations. No `@ControllerAdvice` global handler either. |
| **Conventional Commits format** | Zero commits follow the required `<type>: description` format (`feat:`, `fix:`, `refactor:`, `docs:`, `chore:`). All 39 commit messages are plain prose. |
