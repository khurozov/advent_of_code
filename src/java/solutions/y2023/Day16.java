package solutions.y2023;

import extra.Direction;
import extra.Util;
import solutions.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class Day16 extends Solution {

    public static void main(String[] args) throws Exception {
        Day16 day16 = new Day16();
        day16.part1();
        day16.part2();
    }

    @Override
    public void part1() throws Exception {
        System.out.println(light(input(), Direction.RIGHT, 0, -1));
    }

    @Override
    public void part2() throws Exception {
        char[][] map = input();

        long sum = 0;

        for (int i = 0; i < map.length; i++) {
            sum = Math.max(sum, light(map, Direction.RIGHT, i, -1));
            sum = Math.max(sum, light(map, Direction.LEFT, i, map[0].length));
        }

        for (int j = 0; j < map[0].length; j++) {
            sum = Math.max(sum, light(map, Direction.DOWN, -1, j));
            sum = Math.max(sum, light(map, Direction.UP, map.length, j));
        }

        System.out.println(sum);
    }

    private char[][] input() throws IOException {
        return Files.readAllLines(this.inputFile())
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static long light(char[][] map, Direction cur, int iStart, int jStart) {
        Direction[][][] grid = new Direction[map.length][][];
        for (int i = 0; i < map.length; i++) {
            grid[i] = new Direction[map[0].length][];
        }

        lightOn(map, grid, cur, iStart, jStart);

        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .count();
    }

    private static void lightOn(char[][] map, Direction[][][] grid, Direction cur, int i, int j) {
        if (Util.inRange(map, i, j)) {
            if (grid[i][j] == null) {
                grid[i][j] = new Direction[]{cur};
            } else {
                Direction[] dirs = new Direction[grid[i][j].length + 1];

                for (int k = 0; k < grid[i][j].length; k++) {
                    if (grid[i][j][k] == cur) return;
                    dirs[k] = grid[i][j][k];
                }
                dirs[grid[i][j].length] = cur;

                grid[i][j] = dirs;
            }
        }

        i += cur.i;
        j += cur.j;

        if (Util.inRange(map, i, j)) {
            Direction[] nextLights = mirrored(cur, map[i][j]);
            for (Direction next : nextLights) {
                lightOn(map, grid, next, i, j);
            }
        }
    }

    private static Direction[] mirrored(Direction d, char m) {
        return switch (m) {
            case '.' -> new Direction[]{d};
            case '/' -> new Direction[]{switch (d) {
                case UP -> Direction.RIGHT;
                case RIGHT -> Direction.UP;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.DOWN;
            }};
            case '\\' -> new Direction[]{switch (d) {
                case UP -> Direction.LEFT;
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.RIGHT;
                case LEFT -> Direction.UP;
            }};
            case '|' -> switch (d) {
                case UP, DOWN -> new Direction[]{d};
                case RIGHT, LEFT -> new Direction[]{Direction.UP, Direction.DOWN};
            };
            case '-' -> switch (d) {
                case RIGHT, LEFT -> new Direction[]{d};
                case UP, DOWN -> new Direction[]{Direction.RIGHT, Direction.LEFT};
            };
            default -> throw new IllegalArgumentException(m + " can't be in map");
        };
    }
}
