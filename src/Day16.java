import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class Day16 {
    private enum Light {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        private final int i;
        private final int j;

        Light(int i, int j) {
            this.i = i;
            this.j = j;
        }

        private Light[] mirrored(char m) {
            return switch (m) {
                case '.' -> new Light[]{this};
                case '/' -> new Light[]{switch (this) {
                    case UP -> RIGHT;
                    case RIGHT -> UP;
                    case DOWN -> LEFT;
                    case LEFT -> DOWN;
                }};
                case '\\' -> new Light[]{switch (this) {
                    case UP -> LEFT;
                    case RIGHT -> DOWN;
                    case DOWN -> RIGHT;
                    case LEFT -> UP;
                }};
                case '|' -> switch (this) {
                    case UP, DOWN -> new Light[]{this};
                    case RIGHT, LEFT -> new Light[]{UP, DOWN};
                };
                case '-' -> switch (this) {
                    case RIGHT, LEFT -> new Light[]{this};
                    case UP, DOWN -> new Light[]{RIGHT, LEFT};
                };
                default -> throw new IllegalArgumentException(m + " can't be in map");
            };

        }
    }

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        System.out.println(light(input(), Light.RIGHT, 0, -1));
    }

    private static void part2() throws IOException {
        char[][] map = input();

        long sum = 0;

        for (int i = 0; i < map.length; i++) {
            sum = Math.max(sum, light(map, Light.RIGHT, i, -1));
            sum = Math.max(sum, light(map, Light.LEFT, i, map[0].length));
        }

        for (int j = 0; j < map[0].length; j++) {
            sum = Math.max(sum, light(map, Light.DOWN, -1, j));
            sum = Math.max(sum, light(map, Light.UP, map.length, j));
        }

        System.out.println(sum);
    }

    private static char[][] input() throws IOException {
        return Files.readAllLines(Path.of("src", "Day16.txt"))
                .stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private static long light(char[][] map, Light light, int iStart, int jStart) {
        Light[][][] grid = new Light[map.length][][];
        for (int i = 0; i < map.length; i++) {
            grid[i] = new Light[map[0].length][];
        }

        lightOn(map, grid, light, iStart, jStart);

        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .count();
    }

    private static void lightOn(char[][] map, Light[][][] grid, Light light, int i, int j) {
        if (inRange(map, i, j)) {
            if (grid[i][j] == null) {
                grid[i][j] = new Light[]{light};
            } else {
                Light[] lights = new Light[grid[i][j].length + 1];

                for (int k = 0; k < grid[i][j].length; k++) {
                    if (grid[i][j][k] == light) return;
                    lights[k] = grid[i][j][k];
                }
                lights[grid[i][j].length] = light;

                grid[i][j] = lights;
            }
        }

        i += light.i;
        j += light.j;

        if (inRange(map, i, j)) {
            Light[] nextLights = light.mirrored(map[i][j]);
            for (Light next : nextLights) {
                lightOn(map, grid, next, i, j);
            }
        }
    }

    private static boolean inRange(char[][] map, int i, int j) {
        return i >= 0 && i < map.length && j >= 0 && j < map[0].length;
    }
}
