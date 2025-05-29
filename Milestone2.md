
---

marp: true
author: Ana Matic, Jovana Kisin, Pavle Tomanovic, Viktoria Paschinger
version: 3.0.0
theme: a4-document
paginate: true
header: ' '
footer: 'Advanced Web Technologies 2025'

---

# Project Specification Study Budy

## Description
This project is a web application designed to help students manage their course assignments and tasks. It allows users to create, view, update, and delete courses, assignments, and tasks.

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

## Note on Authentication

Since the project does **not require registration or authentication**, all resources and actions are tied to a **single, hardcoded user**.  

---


## Changes to the UI
- Added a "**?**" button so that the user can go back to the  welcome page (US1) (see Paperprototype.pdf in the docs folder of the Project)
- Based on the previous feedback (MS1) added descriptive text for the '**+**" button to mark explicitly its functionality (see updated Paperprototype.pdf)
- 

## Database Setup

This project includes a sample H2 database that mimics MySql database with test data.

### Steps:
1. Clone the repository (sample database included)
2. Run the application: `./mvnw spring-boot:run`
3. Access H2 Console: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:file:./data/studybuddy_db`
   - Username: `studybuddy`  
   - Password: `studybuddy`

In the h2-console you can see the existing data and add new one, which will be reflected in the UI.
### Sample Data Included:
- Test user
- Sample courses, assignments, and tasks

