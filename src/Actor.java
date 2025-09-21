import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public abstract class Actor {
    ArrayList<Polygon> shapes = new ArrayList<>();
    Cell loc;
    ArrayList<Item> inventory = new ArrayList<>();

    public abstract Color getColor();
    public abstract void updateShapes();

    public void paint(Graphics g) {
        g.setColor(getColor());
        for (Polygon p : shapes) {
            g.fillPolygon(p);
            g.setColor(Color.BLACK);
            g.drawPolygon(p);
            g.setColor(getColor());
        }
    }

    public void collectFood() {
        ArrayList<Item> items = loc.getItems();
        items.removeIf(item -> {
            if ((this instanceof Cat && item instanceof Tuna) ||
                (this instanceof Dog && item instanceof Bone) ||
                (this instanceof Bird && item instanceof Seed)) {
                inventory.add(item);
                return true;
            }
            return false;
        });
    }

    public void moveTowards(Cell target, Grid grid) {
        if (target == null || target == loc) return;

        List<Cell> path = findPath(loc, target, grid);
        if (!path.isEmpty()) {
            loc = path.get(0); // Move one step along the path
            collectFood();
            updateShapes();
        }
    }

    private List<Cell> findPath(Cell start, Cell target, Grid grid) {
        int cols = grid.cells.length;
        int rows = grid.cells[0].length;

        boolean[][] visited = new boolean[cols][rows];
        Cell[][] prev = new Cell[cols][rows];

        Queue<Cell> queue = new LinkedList<>();
        queue.add(start);

        int startC = (start.x - 10) / Cell.size;
        int startR = (start.y - 10) / Cell.size;
        visited[startC][startR] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int c = (current.x - 10) / Cell.size;
            int r = (current.y - 10) / Cell.size;

            if (current == target) break;

            for (int i = 0; i < 4; i++) {
                int nc = c + dx[i];
                int nr = r + dy[i];
                if (nc >= 0 && nc < cols && nr >= 0 && nr < rows &&
                        !visited[nc][nr] && grid.cells[nc][nr].isPassable()) {
                    visited[nc][nr] = true;
                    prev[nc][nr] = current;
                    queue.add(grid.cells[nc][nr]);
                }
            }
        }

        // Reconstruct path
        List<Cell> path = new ArrayList<>();
        Cell step = target;
        while (step != null && step != start) {
            path.add(0, step);
            int c = (step.x - 10) / Cell.size;
            int r = (step.y - 10) / Cell.size;
            step = prev[c][r];
        }

        return path;
    }
}
