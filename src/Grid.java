import java.awt.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Grid {
    Cell[][] cells = new Cell[20][20];
    private Random random = new Random();

    public Grid() {
        // Initialize cells
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(10 + Cell.size * i, 10 + Cell.size * j);
            }
        }

        // Add some random food items
        placeFoodRandomly(new Bone());
        placeFoodRandomly(new Tuna());
        placeFoodRandomly(new Seed());
    }

    // Paint all cells and optional mouse highlight
    public void paint(Graphics g, Point mousePos) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].paint(g, mousePos);
            }
        }

        // Paint cell details on the side
        paintCellDetails(g, mousePos);
    }

    private void paintCellDetails(Graphics g, Point mousePos) {
        g.setColor(Color.WHITE);
        g.fillRect(720, 0, 304, 720);
        g.setColor(Color.BLACK);
        g.drawRect(720, 0, 304, 720);

        Optional<Cell> cellOpt = cellAtPoint(mousePos);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        if (cellOpt.isPresent()) {
            Cell cell = cellOpt.get();
            g.drawString("Cell Details:", 730, 30);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Position: (" + cell.x + ", " + cell.y + ")", 730, 60);
            g.drawString("Type: " + cell.getType(), 730, 85);
            g.drawString("Elevation: " + cell.getElevation() + "m", 730, 110);

            int col = (cell.x - 10) / Cell.size;
            int row = (cell.y - 10) / Cell.size;
            g.drawString("Grid Position: [" + col + ", " + row + "]", 730, 135);

            if (!cell.getItems().isEmpty()) {
                g.drawString("Contains: " + cell.getItems().size() + " item(s)", 730, 160);
            }
        } else {
            g.drawString("No cell at mouse position", 730, 30);
            if (mousePos != null) {
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                g.drawString("Mouse: (" + mousePos.x + ", " + mousePos.y + ")", 730, 55);
            }
        }
    }

    // Returns cell at column/row if in bounds
    public Optional<Cell> cellAtColRow(int c, int r) {
        if (c < 0 || c >= cells.length || r < 0 || r >= cells[0].length) {
            return Optional.empty();
        }
        return Optional.of(cells[c][r]);
    }

    // Returns cell at point
    public Optional<Cell> cellAtPoint(Point p) {
        if (p == null) return Optional.empty();
        int col = (p.x - 10) / Cell.size;
        int row = (p.y - 10) / Cell.size;

        if (col < 0 || col >= cells.length || row < 0 || row >= cells[0].length) {
            return Optional.empty();
        }
        return Optional.of(cells[col][row]);
    }

    // Place a food item randomly in a non-rock cell
    private void placeFoodRandomly(Item item) {
        while (true) {
            int c = random.nextInt(cells.length);
            int r = random.nextInt(cells[0].length);
            Cell cell = cells[c][r];
            if (cell.isPassable() && cell.getItems().isEmpty()) {
                cell.addItem(item);
                break;
            }
        }
    }
}
