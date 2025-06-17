# Observer Pattern - Generic Subject & Observer in Java

This repository contains a clean, generic, and extensible implementation of the **Observer Pattern** in Java, designed with flexibility and simplicity in mind.

---

## Features

- Generic `Subject<T>` base class supporting:
  - Attaching, detaching, and managing multiple observers.
  - Broadcasting updates to all observers.
  - Multicasting updates excluding one or multiple observers.
  - Whispering notifications to a single observer.
  - Payload support with generic data type.
- Simple `Observer<T>` contract with overloaded `received()` methods.
- Thread-safe ready (can be adapted easily).
- Minimal dependencies, pure Java SE.

---

## Getting Started

### Clone the repository:

```bash
git clone https://github.com/heia-sarange-herrera/observer-pattern.git
cd observer-pattern-java
