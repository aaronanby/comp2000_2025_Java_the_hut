# Animals Collecting Food Simulation

## Overview
This program is a simple simulation of animals (Cats, Dogs, Birds) moving across a grid world to collect their preferred food (Tuna, Bones, Seeds). Each animal automatically seeks the nearest matching food, moves step-by-step toward it while avoiding impassable terrain (rocks), and collects the food into its inventory.

The program is implemented in **Java** using Swing for graphics.

---

## Object-Oriented Design

### Inheritance
Inheritance was used to reduce duplication and provide a clean hierarchy:

- **`Actor` (abstract class)**  
  - Defines shared behavior for all animals: painting shapes, moving across the grid, finding paths, and collecting food.  
  - Subclasses: `Cat`, `Dog`, and `Bird` each override:
    - `getColor()` → to draw with their own color.  
    - `updateShapes()` → to define unique body shapes.  

- **`Item` (abstract class)**  
  - Provides a shared interface for all food items.  
  - Subclasses: `Tuna`, `Bone`, and `Seed`, each with their own name and color.  

This design means that adding a **new animal** (e.g., `Mouse`) or **new food type** (e.g., `Cheese`) requires minimal changes—just extend the appropriate base class.

### Generics and Collections
The program uses Java’s generic collections (`ArrayList`, `Optional`, `Queue`):

- **`ArrayList<Actor>`** stores all animals in the stage.  
- **`ArrayList<Item>`** stores food items in each cell.  
- **`Queue<Cell>`** (BFS search) is used for pathfinding when animals move toward food.  
- **`Optional<Cell>`** is returned for safe handling of possible null results when looking up cells.

Generics ensure **type safety** (e.g., a cell’s item list can only contain `Item` objects), making the code easier to reason about and reducing runtime errors.

---

## Instructions to Compile and Run

### Requirements
- Java 17 or later (earlier versions may also work).  
- Any standard Java compiler (e.g., `javac`).  

### Files in the Project
 - Actor.java
 - Bird.java
 - Bone.java
 - Cat.java
 - Cell.java
 - Dog.java
 - Grid.java
 - Item.java
 - Main.java
 - Seed.java
 - Stage.java
 - Tuna.java


### How to Compile
Open a terminal in the project folder and run:
 b ash 

javac *.java

### How to run
java Main


## Expected Behaviour
 - A window opens showing a 20×20 grid world.
 - Cat, Dog, and Bird appear at starting positions.
 - Food items (Tuna, Bone, Seed) appear randomly on the grid.
 - Each animal moves step-by-step toward its nearest matching food, avoiding rock cells.
 - When food is reached, it is collected into the animal’s inventory and disappears from the grid.

## Extending the Program
- To add a new animal: extend Actor and implement its shape and color.
- To add a new food: extend Item and define its name and color.
- Update Stage.findNearestFood and Actor.collectFood with the new pairing.


