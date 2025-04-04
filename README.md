# COIS 2240H – Software Design & Modelling  
## Assignment 3 — UML Design and Modelling  

**Total Points:** 100  
**Due Date:** April 4th, 2025  

---

### 📢 Notes

- **Group Work:** Max 3 students per group. Individual submissions allowed but not encouraged.
- **Integrity Warning:** Unauthorized use of generative AI or plagiarism will be treated as academic misconduct.
- **Late Policy:**  
  - 10% deducted per day  
  - Max 5 days late  
  - Second submissions after the due date will also be marked late  

---

## 🎯 Objectives

- Extend and improve an existing software system using design patterns
- Implement file storage and data retrieval
- Refactor code for maintainability
- Write unit tests to ensure reliability
- Practice version control with Git
- _(Optional Bonus)_ Build a GUI with JavaFX

---

## 📁 Task 0: GitHub Repository and Commits (20 Points)

1. Create a GitHub repository named `COIS-2240-Assignment3`, or fork from  
   [this repo](https://github.com/taher-ghaleb/COIS-2240-Assignment3)
2. Clone the repository
3. Create an Eclipse project named `RentalProject`
4. Copy assignment source files into your project
5. First commit = original source code
6. **At least 11 commits** to track Task 1 & 2 progress
7. **Make repo public** and share the URL on Blackboard
8. All group members should contribute and commit using their own GitHub accounts

---

## 🔧 Task 1: Software Extension (50 Points)

You’ll extend the existing *Vehicle Rental System* with new features and improvements.

> 🚨 No AI-generated code allowed. Violations = academic misconduct.

### Subtasks

1. **Singleton Design Pattern** (9 Points)  
   - Refactor `RentalSystem` to a Singleton  
   - Add `getInstance()` method  
   - Update `RentalSystemApp` to use this instance  

2. **File-Based Storage** (9 Points)  
   - Implement:
     - `saveVehicle()` → `vehicles.txt`
     - `saveCustomer()` → `customers.txt`
     - `saveRecord()` → `rental_records.txt`

3. **Load Saved Data** (9 Points)  
   - Create `loadData()` in `RentalSystem`  
   - Load from file on startup

4. **Prevent Duplicate Entries** (9 Points)  
   - Validate unique license plate & customer ID  
   - Return `true/false` based on success  

5. **Vehicle Constructor Refactor** (9 Points)  
   - Extract repeated logic into `capitalize(String input)` helper method

6. **Program Execution & Output** (5 Points)  
   - Add customers & vehicles  
   - Perform rental/return operations  
   - Save output or screenshots

### Submission

- `Task1_Code.zip` – all `.java` files  
- `Task1_Output.txt/pdf` – console output  
- `Task1_Git.jpg/png/pdf` – at least **6 Git commit** screenshots  

---

## 🧪 Task 2: Testing (30 Points)

Use **JUnit 5** to implement unit tests in a class named `VehicleRentalTest`.

### Subtasks

1. **License Plate Validation** (10 Points)  
   - Add `isValidPlate()` to `Vehicle`  
   - Update `setLicensePlate()`  
   - Test valid/invalid formats in `testLicensePlateValidation()`

2. **Rent/Return Validation** (10 Points)  
   - Test renting, double renting, returning, and double returning in `testRentAndReturnVehicle()`

3. **Singleton Validation** (10 Points)  
   - Use Java Reflection to test private constructor  
   - Confirm only one instance via `getInstance()`  
   - Test in `testSingletonRentalSystem()`

### Submission

- `VehicleRentalTest.java`  
- `Task2_Output.jpg/png/pdf` – test result screenshot  
- `Task2_Git.jpg/png/pdf` – at least **3 Git commit** screenshots  

---

## 🎨 [Optional] Task 3: GUI with JavaFX (Bonus 25 Points)

Create `RentalSystemGUI.java` with a user-friendly JavaFX interface.

### Requirements

- Replace console with GUI  
- Handle all operations from `RentalSystemApp`  
- Use components: buttons, text fields, combo boxes, etc.  
- GUI must run **without errors** for bonus to be awarded

### Submission

- `RentalSystemGUI.java`  
- JavaFX **only** (No Swing, AWT, or Applets)  
- Git usage expected (screenshots not required)

---

## 📌 Instructor  
**Taher Ghaleb**  
**Winter 2025**

---
