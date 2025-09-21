import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Cell extends Rectangle {
    static int size = 35;
    private String type;
    private int elevation;
    private ArrayList<Item> items = new ArrayList<>();

    public Cell(int x, int y) {
        super(x, y, size, size);

        // Random biome assignment
        String[] types = {"Grass", "Water", "Rock", "Sand", "Forest"};
        this.type = types[(int)(Math.random() * types.length)];
        this.elevation = (int)(Math.random() * 100);
    }

    public void paint(Graphics g, Point mousePos) {
        // Set color based on biome type
        switch(type) {
            case "Grass": g.setColor(new Color(60, 179, 113)); break;  // green
            case "Water": g.setColor(new Color(64, 164, 223)); break;  // blue
            case "Rock": g.setColor(new Color(112, 128, 144)); break;  // grey
            case "Sand": g.setColor(new Color(237, 201, 175)); break;  // tan
            case "Forest": g.setColor(new Color(34, 139, 34)); break;  // dark green
            default: g.setColor(Color.WHITE);
        }

        g.fillRect(x, y, size, size);

        // Highlight if mouse is over
        if (mousePos != null && contains(mousePos)) {
            g.setColor(Color.GRAY);
            g.drawRect(x, y, size, size);
        }

        // Draw items (food) in the center
        for (Item item : items) {
            g.setColor(item.getColor());
            g.fillOval(x + size/4, y + size/4, size/2, size/2);
        }

        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
    }

    public boolean contains(Point p) {
        return p != null && super.contains(p);
    }

    public String getType() { return type; }
    public int getElevation() { return elevation; }
    public ArrayList<Item> getItems() { return items; }

    // Check if animal can move into this cell
    public boolean isPassable() {
        return !type.equals("Rock");
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
