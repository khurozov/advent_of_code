package day10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.LinkedList;

public class Part2 {
    public static void main(String[] args) throws IOException {

        Pipe[][] pipes = Util.readInput();

        LinkedList<Coordinate> path = Util.findPath(pipes);
        LinkedList<Coordinate> enclosedByLoop = new LinkedList<>();
        Polygon polygon = Util.pathToPolygon(path);

        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                if (polygon.contains(i,j) && !path.contains(coordinate)) {
                    enclosedByLoop.add(coordinate);
                }
            }
        }
        System.out.println(enclosedByLoop.size());

        JFrame frame = new JFrame("Pipes");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (g instanceof Graphics2D g2d) {
                    double scale = 6;
                    g.setColor(Color.BLACK);
                    AffineTransform savedTransform = g2d.getTransform();

                    AffineTransform transform = new AffineTransform();
                    transform.setToScale(scale, scale);

                    g2d.setTransform(transform);
                    g2d.fillPolygon(polygon);

                    g2d.setTransform(savedTransform);
                    g.setColor(Color.YELLOW);

                    for (Coordinate c : enclosedByLoop) {
                        g.fillRect((int) (c.x()*scale - scale/2), (int) (c.y()*scale - scale/2), (int) scale, (int) scale);
                    }
                }
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }
}
