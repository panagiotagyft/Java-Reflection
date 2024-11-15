<div align="center">
  <h1>M135 - Advanced Programming Methods</h1>
</div>
<div align="center">
  <h2>Java Reflection</h2>
</div>
<div align="center">
  <h2>Winter Semester 2024–2025</h2>
</div>

Panagiota Gyftou - 7115112400025

## Compile & Run (2 options)

#### Option 1: Manual Compilation and Execution

###### 1. Compile the Program

`javac -d . src/Main.java modules/Config.java modules/Utils.java modules/FileManager.java modules/Reflection.java`

###### 2. Run the Program/Tests

`java -cp . Main <input-file> <output-file> <value-of-N>`

- **`<input-file>`**: Path to a text file with one type name per line.
- **`<output-file>`**: Path to the output file for storing results.
- **`<value-of-N>`**: Number `N` for calculating the top N types (e.g., 10).

**Run Examples:**

```bash
java -cp . Main input.txt output.txt 3
```

#### Option 2: Using a Makefile for compilation and execution

###### 1. Compile the Program/Tests

The program can be compiled using one of the following options:

- `all`: Compiles both the main program and test classes.
- `program`: Compiles all modules into .class files in the build/program directory and compiles Main.java.
- `program_tests`: Compiles all test classes into .class files in the build/tests directory.

The following commands can be used to compile:

```bash
make all

make program

make program_tests
```

###### 2. Run the Program/Tests

Compilation and execution can also be done using the Makefile.

- `run`: Runs both the main program and all tests sequentially.
- `run-program`: Executes the main program. Optional arguments can be passed via the ARGS variable:
- `run-tests`: Executes all test cases using JUnit.

```bash
# ARGS="<input-file> <output-file> <value-of-N>"
# e.g. ARGS="./classes.txt ./output.txt 3"

make run ARGS="your arguments here"

make run-program ARGS="your arguments here"

make run-tests

```

# Reflection Class Overview

The `Reflection` class, located in the `modules` package, provides advanced functionality for analyzing and manipulating Java classes through reflection. The main features include inspecting fields, methods, superclasses, and subtypes. Below is an overview of its capabilities.

## Key Features

### 1. Fields and Methods Analysis

The `Reflection` class allows you to analyze the fields and methods of Java classes:

- **Declared Fields (`DeclaredFields`)**: Determines the number of fields declared in a given class.
- **Total Fields (`TotalFields`)**: Computes the total number of fields, including inherited public and protected fields from superclasses.
- **Declared Methods (`DeclaredMethods`)**: Determines the number of methods declared in a given class, avoiding duplicates.
- **Total Methods (`TotalMethods`)**: Computes the total number of methods, including public and protected methods inherited from superclasses.

### 2. Superclass and Subtype Relationships

The `Reflection` class also offers capabilities to analyze class relationships:

- **Supertypes (`Supertypes`)**: Retrieves all superclasses and interfaces of a given class. Uses recursive traversal to determine the full chain of inheritance, excluding `Object.class`.
- **Subtypes (`Subtypes`)**: Determines the subtypes of a given class by examining the relationship in the opposite direction (from superclass to subclasses). Uses a grouped map of classes and their supertypes for efficient lookup.

## Fields and Auxiliary Maps

The class maintains several maps to store information collected during reflection:

- **`declaredFields` and `totalFields`**: Stores the count of declared and total fields for each class.
- **`declaredMethods` and `totalMethods`**: Stores the count of declared and total methods for each class.
- **`supertypes` and `subtypes`**: Stores the count of superclasses and subtypes for each class.
- **Auxiliary Maps**:
  - **`pairs_supertypes`**: Maintains a map of classes and their corresponding supertypes.
  - **`groupedMap`**: Groups classes by their supertype for easy access to subtypes.
