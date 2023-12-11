import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class Day10 {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private record Coordinate(int x, int y) {
    }

    private record Pipe(char c, Direction dir1, Direction dir2) {
        Direction nextDir(Direction dir) {
            if (dir == dir1) return dir2;
            if (dir == dir2) return dir1;
            return Direction.UNKNOWN;
        }

        static Pipe parse(char c) {
            return switch (c) {
                case '|' -> new Pipe(c, Direction.NORTH, Direction.SOUTH);
                case '-' -> new Pipe(c, Direction.EAST, Direction.WEST);
                case 'L' -> new Pipe(c, Direction.NORTH, Direction.EAST);
                case 'J' -> new Pipe(c, Direction.NORTH, Direction.WEST);
                case '7' -> new Pipe(c, Direction.SOUTH, Direction.WEST);
                case 'F' -> new Pipe(c, Direction.SOUTH, Direction.EAST);
                default -> new Pipe(c, Direction.UNKNOWN, Direction.UNKNOWN);
            };
        }

        boolean hasNorth() {
            return dir1 == Direction.NORTH || dir2 == Direction.NORTH;
        }

        boolean hasSouth() {
            return dir1 == Direction.SOUTH || dir2 == Direction.SOUTH;
        }

        boolean hasEast() {
            return dir1 == Direction.EAST || dir2 == Direction.EAST;
        }

        boolean hasWest() {
            return dir1 == Direction.WEST || dir2 == Direction.WEST;
        }
    }

    enum Direction {
        NORTH(-1, 0),
        SOUTH(1, 0),
        EAST(0, 1),
        WEST(0, -1),
        UNKNOWN(0, 0);

        final int i;
        final int j;

        Direction(int i, int j) {
            this.i = i;
            this.j = j;
        }

        Direction reverse() {
            return switch (this) {
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case EAST -> WEST;
                case WEST -> EAST;
                case UNKNOWN -> UNKNOWN;
            };
        }
    }


    private static void part1() throws IOException {
        Pipe[][] pipes = readInput();
        LinkedList<Coordinate> path = findPath(pipes);
        System.out.println(path.size() / 2);
    }

    private static void part2() throws IOException {

        Pipe[][] pipes = readInput();

        LinkedList<Coordinate> path = findPath(pipes);
        LinkedList<Coordinate> enclosedByLoop = new LinkedList<>();
        Polygon polygon = pathToPolygon(path);

        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                Coordinate coordinate = new Coordinate(i, j);
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

                    for (Coordinate c : enclosedByLoop) {
                        g.fillRect((int) (c.x() * scale - scale / 2), (int) (c.y() * scale - scale / 2), (int) scale, (int) scale);
                    }
                }
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }

    private static Pipe[][] readInput() throws IOException {
        return Files.readAllLines(Path.of("src", "Day10.txt"))
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

    private static LinkedList<Coordinate> findPath(Pipe[][] pipes) {
        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                if (pipes[i][j].c() == 'S') {
                    if (i > 0 && pipes[i - 1][j].hasSouth()) {
                        // go north
                        return findPathHelper(pipes, i - 1, j, Direction.SOUTH);
                    }
                    if (i < pipes.length - 1 && pipes[i + 1][j].hasNorth()) {
                        // can go south
                        return findPathHelper(pipes, i + 1, j, Direction.NORTH);
                    }
                    if (j > 0 && pipes[i][j - 1].hasEast()) {
                        // can go west
                        return findPathHelper(pipes, i, j - 1, Direction.EAST);
                    }
                    if (j < pipes[i].length - 1 && pipes[i][j + 1].hasWest()) {
                        // can go east
                        return findPathHelper(pipes, i, j + 1, Direction.WEST);
                    }
                }
            }
        }

        return new LinkedList<>();
    }

    private static LinkedList<Coordinate> findPathHelper(Pipe[][] pipes, int i, int j, Direction from) {
        Pipe p = pipes[i][j];
        LinkedList<Coordinate> coordinates = new LinkedList<>();
        coordinates.add(new Coordinate(i, j));

        do {
            Direction dir = p.nextDir(from);
            if (dir == Direction.UNKNOWN) return new LinkedList<>();

            i += dir.i;
            j += dir.j;
            coordinates.add(new Coordinate(i, j));

            if (i < 0 || i >= pipes.length) return new LinkedList<>();
            if (j < 0 || j >= pipes[i].length) return new LinkedList<>();

            p = pipes[i][j];
            from = dir.reverse();
        } while (p.c() != 'S');
        return coordinates;
    }

    private static Polygon pathToPolygon(LinkedList<Coordinate> path) {
        Polygon polygon = new Polygon();
        for (Coordinate coordinate : path) {
            polygon.addPoint(coordinate.x(), coordinate.y());
        }
        return polygon;
    }
}
