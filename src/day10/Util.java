package day10;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

class Util {
    static Pipe[][] readInput() throws IOException {
        return Files.readAllLines(Path.of("src", "day10", "input"))
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

    static LinkedList<Coordinate> findPath(Pipe[][] pipes) {
        for (int i = 0; i < pipes.length; i++) {
            for (int j = 0; j < pipes[i].length; j++) {
                if (pipes[i][j].c() == 'S') {
                    if (i > 0 && pipes[i - 1][j].hasSouth()) {
                        // go north
                        return findPathHelper(pipes, i-1, j, Direction.SOUTH);
                    }
                    if (i < pipes.length -1 && pipes[i + 1][j].hasNorth()) {
                        // can go south
                        return findPathHelper(pipes, i+1, j, Direction.NORTH);
                    }
                    if (j > 0 && pipes[i][j-1].hasEast()) {
                        // can go west
                        return findPathHelper(pipes, i, j-1, Direction.EAST);
                    }
                    if (j < pipes[i].length -1 && pipes[i][j+1].hasWest()) {
                        // can go east
                        return findPathHelper(pipes, i, j+1, Direction.WEST);
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

    static Polygon pathToPolygon(LinkedList<Coordinate> path) {
        Polygon polygon = new Polygon();
        for (Coordinate coordinate : path) {
            polygon.addPoint(coordinate.x(), coordinate.y());
        }
        return polygon;
    }
}
