import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stage {
    Grid grid;
    ArrayList<Actor> actors = new ArrayList<>();

    public Stage() {
        grid = new Grid();

        // Create animals and add them to the list
        actors.add(new Cat(grid.cellAtColRow(0, 0).get()));
        actors.add(new Dog(grid.cellAtColRow(0, 15).get()));
        actors.add(new Bird(grid.cellAtColRow(12, 9).get()));
    }

    // Paint the stage (grid + actors)
    public void paint(Graphics g, Point mousePos) {
        // Paint the grid first
        grid.paint(g, mousePos);

        // Paint all actors
        for (Actor actor : actors) {
            actor.paint(g);
        }
    }

    // Move all actors one step toward the nearest food
    public void update() {
        for (Actor actor : actors) {
            Optional<Cell> nearestFood = findNearestFood(actor);
            nearestFood.ifPresent(cell -> actor.moveTowards(cell, grid));
        }
    }

    // Find the nearest food for the given actor
    private Optional<Cell> findNearestFood(Actor actor) {
        Cell current = actor.loc;
        int minDist = Integer.MAX_VALUE;
        Cell nearest = null;

        for (int i = 0; i < grid.cells.length; i++) {
            for (int j = 0; j < grid.cells[i].length; j++) {
                Cell cell = grid.cells[i][j];
                for (Item item : cell.getItems()) {
                    // Only pick food that matches the animal
                    if ((actor instanceof Cat && item instanceof Tuna) ||
                        (actor instanceof Dog && item instanceof Bone) ||
                        (actor instanceof Bird && item instanceof Seed)) {

                        int dist = Math.abs(cell.x - current.x) + Math.abs(cell.y - current.y);
                        if (dist < minDist && cell.isPassable()) {
                            minDist = dist;
                            nearest = cell;
                        }
                    }
                }
            }
        }
        return Optional.ofNullable(nearest);
    }
}