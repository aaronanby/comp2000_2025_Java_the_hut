import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, MouseMotionListener {

    Stage stage;
    Point mousePos = null;
    Timer timer;

    public Main() {
        stage = new Stage();
        setPreferredSize(new Dimension(740, 720));
        setBackground(Color.WHITE);

        addMouseMotionListener(this);

        // Timer triggers update every 300ms
        timer = new Timer(300, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        stage.paint(g, mousePos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stage.update();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Animals Collecting Food");
        Main mainPanel = new Main();
        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
