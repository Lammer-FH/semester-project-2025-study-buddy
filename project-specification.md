---
marp: true
author: Ana Matic, Jovana Kisin, Pavle Tomanovic, Viktoria Paschinger
version: 1.0.0
theme: a4-document
paginate: true
header: ' '
footer: 'Advanced Web Frameworks 2025'
---

# Project Specification - Course-Based Study Planner

## Resource Description

| Resource   | Description                                  | Relationships                        |
|------------|----------------------------------------------|--------------------------------------|
| `user`     | A student account                            | Has many Courses                     |
| `course`   | A university course                          | Belongs to User, has many Assignments|
|`assignment`| A major task with a deadline                 | Belongs to Course, has many Tasks    |
| `task`     | A subtask of an assignment created by a User | Belongs to Assignment and User       |

---

## User Stories

---

### 🟦 User Story #1 – Static

**Story:**  
As a new user, I want to see a welcome page explaining the app's purpose and how it helps manage course assignments and tasks, so that I know what to expect.

**Affected Resources:**  
*(none)*

**Frontend Component:**  
Static Page Frontend Component with hardcoded Information about the App.

---

### 🟩 User Story #2

**Story:**  
As a student, I want to view a list of all upcoming assignments sorted by deadline, so I can see what is due when.

**Affected Resources:**  
`assignment`

**Implementation:**  
GET `users/{id}/assignments?sort=date`

---

### 🟩 User Story #3

**Story:**  
As a student, I want to view all assignments for a specific course, so I can focus on one subject at a time.

**Affected Resources:**  
`course`, `assignment`, `user`

**Implementation:**  
GET `course/{id}/assignments?sort=date`
GET `user/{id}/courses/`
Frontend View: Course List 
Frontend View: Assignment list for specific course

---

### 🟥 User Story #4

**Story:**  
As a student, I want to view the tasks related to a specific assignment, so that I can understand the required steps.

**Affected Resources:**  
`task`, `assignment`, `user`

**Implementation:**  
GET `assignments/{id}/tasks/`
Assignment detail page showing task checklist

---

### 🟥 User Story #5

**Story:**  
As a student, I want to add and check tasks within an assignment, so I can manage my progress.

**Affected Resources:**  
`task`, `assignment`, `user`

**Implementation:**  <!--Sollte hier der Endpoint tasks sein? -->
Task checklist with add/edit
POST `assignments/{id}/tasks/`
PUT `assignments/{id}/tasks/{id}`
Assignment detail page update task checklist
---

### 🟥 User Story #6

**Story:**  
As a student, I want to delete tasks, so I can clean up tasks I don't need anymore.

**Affected Resources:**  
`task`, `assignment`, `user`

**Implementation:**  
DELTE `assignments/{id}/tasks/{id}`
Assignment detail page update task checklist