# ✅ Notes/Todo App API

A Spring Boot REST API for managing tasks/todos — built as a practice project to learn Enums, Partial Updates, Custom Exception Handling, and Pagination.

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (in-memory)
- Lombok
- Bean Validation (Jakarta Validation)
- SLF4J Logging
- Maven

## 🚀 Features
- Create, read, update, delete tasks
- Mark task as complete (partial update — no full body required)
- Filter tasks by priority (LOW / MEDIUM / HIGH)
- Get only pending (incomplete) tasks
- Pagination support for listing tasks
- Centralized exception handling with custom exceptions
- Input validation with meaningful error messages

## 📌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|--------------|
| POST | `/api/tasks/create` | Create a new task |
| GET | `/api/tasks/getAll` | Get all tasks |
| GET | `/api/tasks/getAllPaginated?page=0&size=5` | Get tasks with pagination |
| GET | `/api/tasks/pending` | Get incomplete tasks |
| GET | `/api/tasks/{taskId}` | Get a single task by ID |
| GET | `/api/tasks/filterByPriority?priority=HIGH` | Filter tasks by priority |
| PUT | `/api/tasks/markComplete/{taskId}` | Mark a task as complete |
| PUT | `/api/tasks/update/{taskId}` | Update a task |
| DELETE | `/api/tasks/deleteOne/{taskId}` | Delete a single task |
| DELETE | `/api/tasks/deleteAll` | Delete all tasks |

## ⚠️ Error Handling

This project uses a centralized `@RestControllerAdvice` to handle:
- `TaskNotFoundException` → 404
- `TaskAlreadyCompletedException` → 409
- Validation errors (`@NotBlank`, `@Size`, `@NotNull`) → 400

## ▶️ How to Run

1. Clone the repository
```bash
   git clone https://github.com/your-username/notes-todo-app.git
```
2. Open the project in IntelliJ IDEA
3. Run the main application class
4. The app will start on `http://localhost:8080`

## 🗄️ H2 Console


## 📖 What I Learned
- Using Java Enums with `@Enumerated(EnumType.STRING)` for safe, fixed-value fields
- Partial update pattern (action-based endpoints like `markComplete`)
- Custom exceptions with centralized `@RestControllerAdvice`
- Structured logging with SLF4J instead of `System.out.println`
- Pagination with `Pageable` and `Page<T>`