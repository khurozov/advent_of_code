package solutions.y2023;

import extra.Direction;
import extra.Pair;
import extra.Pipe;
import solutions.Solution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

public class Day10 extends Solution {
    public static void main(String[] args) throws Exception {
        Day10 day10 = new Day10();
        day10.part1();
        day10.part2();
    }

    @Override
    public void part1() throws Exception {
        Pipe[][] pipes = readInput();
        LinkedList<Pair<Integer>> path = findPath(pipes);
        System.out.println(path.size() / 2);
    }

    @Override
    public void part2() throws Exception {
        Pipe[][] pipes = readInput();

        LinkedList<Pair<Integer>> path = findPath(pipes);
        LinkedList<Pair<Integer>> enclosedByLoop = new LinkedList<>();
        Polygon polygon = pathToPolygon(path);

        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                Pair<Integer> coordinate = Pair.of(i, j);
                if (polygon.contains(i, j) && !path.contains(coordinate)) {
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

                    for (Pair<Integer> c : enclosedByLoop) {
                        g.fillRect((int) (c.left() * scale - scale / 2), (int) (c.right() * scale - scale / 2), (int) scale, (int) scale);
                    }
                }
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }

    private Pipe[][] readInput() throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(input -> {
                    char[] chars = input.toCharArray();
                    Pipe[] pp = new Pipe[chars.length];
                    for (int i = 0; i < chars.length; i++) {
                        pp[i] = Pipe.parse(chars[i]);
                    }

                    return pp;
                })
                .toArray(Pipe[][]::new);
    }

    private static LinkedList<Pair<Integer>> findPath(Pipe[][] pipes) {
        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                if (pipes[i][j].c() == 'S') {
                    if (i > 0 && pipes[i - 1][j].hasDown()) {
                        // go north
                        return findPathHelper(pipes, i - 1, j, Direction.DOWN);
                    }
                    if (i < pipes.length - 1 && pipes[i + 1][j].hasUp()) {
                        // can go south
                        return findPathHelper(pipes, i + 1, j, Direction.UP);
                    }
                    if (j > 0 && pipes[i][j - 1].hasRight()) {
                        // can go west
                        return findPathHelper(pipes, i, j - 1, Direction.RIGHT);
                    }
                    if (j < pipes[i].length - 1 && pipes[i][j + 1].hasLeft()) {
                        // can go east
                        return findPathHelper(pipes, i, j + 1, Direction.LEFT);
                    }
                }
            }
        }

        return new LinkedList<>();
    }

    private static LinkedList<Pair<Integer>> findPathHelper(Pipe[][] pipes, int i, int j, Direction from) {
        Pipe p = pipes[i][j];
        LinkedList<Pair<Integer>> coordinates = new LinkedList<>();
        coordinates.add(Pair.of(i, j));

        do {
            Direction dir = p.nextDir(from);
            if (dir == null) return new LinkedList<>();

            i += dir.i;
            j += dir.j;
            coordinates.add(Pair.of(i, j));

            if (i < 0 || i >= pipes.length) return new LinkedList<>();
            if (j < 0 || j >= pipes[i].length) return new LinkedList<>();

            p = pipes[i][j];
            from = dir.reverse();
        } while (p.c() != 'S');
        return coordinates;
    }

    private static Polygon pathToPolygon(LinkedList<Pair<Integer>> path) {
        Polygon polygon = new Polygon();
        for (Pair<Integer> coordinate : path) {
            polygon.addPoint(coordinate.left(), coordinate.right());
        }
        return polygon;
    }
}
