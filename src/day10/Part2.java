package day10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;

public class Part2 {
    private static final int SCALE = 7;
    public static void main(String[] args) throws IOException {

        Pipe[][] pipes = Util.readInput();

        LinkedList<Coordinate> path = Util.findPath(pipes);
        Polygon polygon = Util.pathToPolygon(path, SCALE);

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
                g.setColor(Color.BLACK);
                g.fillPolygon(polygon);
                g.setColor(Color.YELLOW);


                int n = 0;

                for (int i = 0; i < pipes.length; i++) {
                    for (int j = 0; j < pipes[i].length; j++) {
                        if (polygon.contains(i*SCALE,j*SCALE) && !path.contains(new Coordinate(i, j))) {
                            g.fillRect(i*SCALE - SCALE/2, j*SCALE - SCALE/2, SCALE, SCALE);
                            n++;
                        }
                    }
                }
                System.out.println(n);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }
}
