---

marp: true
author: Ana Matic, Jovana Kisin, Pavle Tomanovic, Viktoria Paschinger
version: 2.0.0
theme: a4-document
paginate: true
header: ' '
footer: 'Advanced Web Frameworks 2025'

---

# Project Specification

## Resource Description

| Resource     | Description                      | Relationships                             |
|--------------|----------------------------------|-------------------------------------------|
| `user`       | A student account                | Has many `courses`                        |
| `course`     | A university course              | Belongs to `user`, has many `assignments` |
| `assignment` | A major task with a deadline     | Belongs to `course`, has many `tasks`     |
| `task`       | A subtask of an assignment       | Belongs to `assignment`                   |

---

## User Stories

---

### 🟦 User Story #1

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
GET `/assignments?sort=date`

**Frontend Component:**  
List of all assignments.

---

### 🟩 User Story #3

**Story:**  
As a student, I want to view all assignments for a specific course, so I can focus on one subject at a time.

**Affected Resources:**  
`course`, `assignment`, `user`

**Implementation:**  
GET `/courses/`
GET `courses/{id}/assignments?sort=date`

**Frontend Component:**  
Course List 
Assignment list for specific course.

---

### 🟩 User Story #4

**Story:**  
As a student, I want an overview of my account, so that i can quickly and easily access all of my school related information.

**Affected Resources:**  
`user`

**Implementation:**  
GET `/users/{id}`

**Frontend Views:**  
- Account information

---

### 🟥 User Story #5
**Story:**  
As a student, I want to manage my courses, so I can organize my semester according to my actual enrollment.

**Affected Resources:**  
`course`

**Implementation:**  
GET `/courses`  
POST `/courses`  
PUT `/courses/{id}`  
DELETE `/courses/{id}`

**Frontend Views:**  
- Course list with add/edit/delete options  
- Form for adding/editing a course

---

### 🟥 User Story #6
**Story:**  
As a student, I want to manage assignments in a course (create, update, delete), so that I can keep the coursework up to date.

**Affected Resources:**  
`assignment`

**Implementation:**  
POST `/courses/{id}/assignments`  
PUT `/assignments/{id}`  
DELETE `/assignments/{id}`

**Frontend Views:**  
- Detail page for course assignments  
- Form for assignment creation or modification

---

### 🟥 User Story #7

**Story:**  
As a student, I want to manage tasks for a specific assignment (create, view, update, delete), so I can stay organized and track my progress.

**Affected Resources:**  
`task`, `assignment`

**Implementation:**  
GET `/assignments/{id}/tasks`
POST `/assignments/{id}/tasks`
PUT `/tasks/{id}`  
DELETE `/tasks/{id}`

**Frontend Views:**  
- Assignment detail page with task checklist  
- Form for task creation or modification

---

## Note on Authentication

Since the project does **not require registration or authentication**, all resources and actions are tied to a **single, hardcoded user**.  

---

