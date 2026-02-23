# Max User Guide

Max is a lightweight task management chatbot designed to help users
track ToDos, Deadlines, and Events efficiently through a simple
command-line or GUI interface.

---

## Quick Start

1. Launch the application.
2. Enter commands in the input field.
3. Press Enter to execute.
4. Use `bye` to exit the application.

---

## Features

### 1. Adding a ToDo

Adds a task without a date.

**Format: todo DESCRIPTION**

**Example: todo Read book**

**Expected Output: [T] Read book**

---

### 2. Adding a Deadline

Adds a task with a due date.

**Format: deadline DESCRIPTION /by YYYY-MM-DD**

**Example: deadline Submit assignment /by 2026-03-05**

**Expected Output:** [D] Submit assignment (by: Mar 5 2026)

Note:

- Date must follow the format `YYYY-MM-DD`.



---



### 3. Adding an Event

Adds a task with a start and end date.

**Format: event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD**

**Example:** event Conference /from 2026-02-20 /to 2026-02-22

**Expected Output:** [E] Conference (from: Feb 20 2026 to: Feb 22 2026)

---


### 4. Listing Tasks

Displays all tasks currently stored.

**Format: list**

---



### 5. Marking a Task as Done

Marks a task as completed.

**Format: mark INDEX**

**Example:** mark 2

---

### 6. Unmarking a Task

Marks a completed task as not done.

**Format: unmark INDEX**

---

### 7. Deleting a Task

Removes a task from the list.

**Format: delete INDEX**

**Example: delete 3**

Note:

- Task numbering starts from 1.

---

### 8. Sorting Tasks Chronologically

Sorts tasks in ascending chronological order based on date.

- Deadlines and Events are sorted by date.

- Tasks without dates (e.g. ToDo) are placed at the end.

- Sorting modifies the list permanently.

**Format: sort**

---

### 9. Exiting the Application

Closes the chatbot.

**Format: bye**
